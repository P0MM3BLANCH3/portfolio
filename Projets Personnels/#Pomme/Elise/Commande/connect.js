const { ModalBuilder, TextInputBuilder, ActionRowBuilder, TextInputStyle } = require('discord.js')
const { checkAdmin, checkPlayer } = require('../../function')

const Connection = require('../../database/Schema/Connection')

module.exports = {
    name: "connect",
    description: 'Connectez-vous et jouez !!',

    run: async(client, interaction, message, args) => {

        const playerCheck = false
        if (await checkAdmin(interaction) || await checkPlayer(interaction, playerCheck))
            return

        const user_connection = await Connection.findOne({
            userID: interaction.user.id
        })

        if (user_connection.connect == true)
            return interaction.reply({
                content: "Vous êtes déjà connecté(e) !",
                ephemeral: true
            })

        const modal = new ModalBuilder()
            .setCustomId('connectModal')
            .setTitle('Connectez-vous !!')

        const username = new TextInputBuilder()
            .setCustomId('username')
            .setLabel('Entrez votre identifiant')
            .setStyle(TextInputStyle.Short)

        const password = new TextInputBuilder()
            .setCustomId('password')
            .setLabel('Entrez votre mot de passe')
            .setStyle(TextInputStyle.Short)

        const firstRow = new ActionRowBuilder().addComponents(username)
        const secondRow = new ActionRowBuilder().addComponents(password)
    
        modal.addComponents(firstRow, secondRow)
        await interaction.showModal(modal)
    }
}