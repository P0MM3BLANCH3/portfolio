const { EmbedBuilder } = require('discord.js')
const { checkAdmin, checkPlayer, checkConnect } = require('../../function')

const Connection = require('../../database/Schema/Connection')

module.exports = {
    name: "wallet",
    description: "Regardez l'ampleur de votre fortune !",

    run: async(client, interaction, message, args) => {

        const playerCheck = false
        if (await checkAdmin(interaction) || await checkPlayer(interaction, playerCheck) || await checkConnect(interaction))
            return

        const user_connection = await Connection.findOne({
            userID: interaction.user.id
        })

        const embed = new EmbedBuilder()
            .setColor('#2CC610')
            .setTitle('Money')
            .setDescription(`Votre fortune s'étend à : \n\nPortefeuille : **${user_connection.wallet} 💰**\nBanque : **${user_connection.bank} 💳**`)

        interaction.reply({
            embeds: [embed],
            ephemeral: true
        })
    }
}