const { EmbedBuilder } = require('discord.js')

module.exports = {
    name: 'sugg',

    run: async(client, message, args) => {
        message.delete()

        if (!args.length) {
            return message.reply({
                epehemral: true,
                content: "Arguments manquants"
            })
        }
        
        const embed = new EmbedBuilder()
            .setColor("#2CC610")
            .setTitle(`Suggestion de ${message.author.username}`)
            .setDescription(args.join(" "))

        message.channel.send({embeds: [embed]})
            .then(messageReact => {
                messageReact.react('✅')
                messageReact.react('⛔')
            })
    }
}