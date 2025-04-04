const { checkAdmin, checkPlayer } = require('../../function')
const { EmbedBuilder, ButtonBuilder, ButtonStyle, ActionRowBuilder } = require('discord.js')

module.exports = {
    name: "start",
    description: 'Que la partie commence !',

    run: async(client, interaction, message, args) => {
        
        const playerCheck = true
        if (await checkAdmin(interaction) || await checkPlayer(interaction, playerCheck))
            return

        const embed = new EmbedBuilder()
            .setColor('#2CC610')
            .setTitle('Avant de commencer !')
            .setDescription(`Avant de commencer voici quelque point :

○ Nous allons vous demander de créer un identifiant et un mot de passe, merci d'en mettre de différent de vos habitude, merci de n'en divulguer aucun des deux.
○ En cas d'oubli vous pouvez demander à Elise en message privé ! (il est préférable que vous reteniez xD)
○ Veillez à respecter le règlement et les autres joueurs. Tout le monde peut avoir sa chance !
○ Merci de respecter les utilisations des salons !

Sur ce, je vous souhaite une bonne partie, on se revoit vite !`)

        const button = new ButtonBuilder()
            .setCustomId('start')
            .setLabel('Commencez !')
            .setStyle(ButtonStyle.Success)

        const row = new ActionRowBuilder().addComponents(button)

        interaction.reply({
            embeds: [embed],
            components: [row],
            ephemeral: true
        })
    }
}