const {  } = require('discord.js')
const { checkAdmin, checkPlayer, checkConnect } = require('../../function')
const Connection = require('../../database/Schema/Connection')

module.exports = {
    name: "orderflow",
    description: 'Faites toutes sortes de transactions !',
	options: [
		{
			name: "give_money",
			description: "Donnez votre l'argent !",
			type: 1,
			options: [
				{
					name: "givemoney_source",
					description: "D'où vient cette argent ?",
					type: 3,
					required: true,
					choices: [
						{
							name: "Porte-feuille",
							value: "wallet"
						},
						{
							name: "Banque",
							value: "bank"
						}
					]
				},
				{
					name: "givemoney_user",
					description: "A qui ?",
					type: 6,
					required: true
				},
				{
					name: "givemoney_amount",
					description: "Combien ?",
					type: 4,
					required: true
				}
			]
		},
		{
			name: "withdraw",
			description: "Récupérez votre argent depuis votre banque !",
			type: 1,
			options: [
				{
					name: "withdraw_amount",
					description: "Combien ?",
					type: 4,
					required: true
				}
			]
		},
		{
			name: "deposit",
			description: "Déposez votre argent dans votre banque !",
			type: 1,
			options: [
				{
					name: "deposit_amount",
					description: "Combien ?",
					type: 4,
					required: true
				}
			]
		}
	],

    run: async(client, interaction, message, args) => {

			const playerCheck = false
			if (await checkAdmin(interaction) || await checkPlayer(interaction, playerCheck) || await checkConnect(interaction))
					return

			const user_connection = await Connection.findOne({
				userId: interaction.user.id
			})
			const amount = interaction.options.getInteger('givemoney_amount') || interaction.options.getInteger('withdraw_amount') || interaction.options.getInteger('deposit_amount') || null

					if (interaction.options.getSubcommand() === "give_money") {
				const user_tagged = await Connection.findOne({
					userId: interaction.options.getUser('givemoney_name').id
				})
				if (interaction.options.getString('givemoney_source') === "wallet") {
					if (user_connection.wallet - amount < 0) {
						return interaction.reply({
							content: "Vous n'avez pas assez sur vous...",
							ephemeral: true
						})
					}
					user_connection.wallet -= amount
					user_tagged += amount
					await user_connection.save()
					await user_tagged.save()

					interaction.reply({
						content: "Transaction terminée ! ✅"
					})

					const embed = new EmbedBuilder()
						.setColor('#2CC610')
						.setTitle(`Transaction ${user_connection.player_surname} => ${user_tagged.player_surname}`)
						.setDescriptiob(`**Montant :** ${amount} 💸`)

					interaction.user_connection.send({
						embeds: [embed]
					})
					interaction.user_tagged.send({
						embeds: [embed]
					})
				}

				if (interaction.options.getString('givemoney_source') === "bank") {
					if (user_connection.bank - amount < 0) {
						return interaction.reply({
							content: "Vous n'avez pas assez en banque...",
							ephemeral: true
						})
					}

					user_connection.bank -= amount
					user_tagged.bank += amount
					await user_connection.save()
					await user_tagged.save()

					interaction.reply({
						content: "Transaction terminée ! ✅"
					})

					const embed = new EmbedBuilder()
						.setColor('#2CC610')
						.setTitle(`Transaction ${user_connection.player_surname} => ${user_tagged.player_surname}`)
						.setDescriptiob(`**Montant :** ${amount} 💸`)

					interaction.user_connection.send({
						embeds: [embed]
					})
					interaction.user_tagged.send({
						embeds: [embed]
					})
				}
			}

			if (interaction.options.getSubcommand() === "deposit") {
				if (user_connection.wallet - amount < 0) {
					return interaction.reply({
						content: "Vous n'avez pas assez sur vous...",
						ephemeral: true
					})
				}

				user_connection.wallet -= amount
				user_connection.bank += amount
				await user_connection.save()

				interaction.reply({
					content: `Transaction terminée ! ✅\n\n**Porte-feuille ➡️ Banque :** ${amount} 💸`,
					ephemeral: true
				})
			}

			if (interaction.options.getSubcommand() === "withdraw") {
				if (user_connection.bank - amount < 0) {
					return interaction.reply({
						content: "Vous n'avez pas assez en banque...",
						ephemeral: true
					})
				}

				user_connection.bank -= amount
				user_connection.wallet += amount
				user_connection.save()

				interaction.reply({
					content: `Transaction terminée ! ✅\n\n**Banque ➡️ Porte-feuille :** ${amount} 💸`,
					ephemeral: true
				})
			}
    }
}