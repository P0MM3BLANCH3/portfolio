const client = require("../index");
const config = require("../config.json");
const Discord = require('discord.js')
const path = require('path')
const Statu = require('../database/Schema/Statu')

client.on("messageCreate", async (message, arg) => {
    let prefix = config.prefix

    if (
        message.author.bot ||
        !message.guild
    )
        return

    if (message.channel.id === config.suggchannel && !message.content.toLowerCase().startsWith('<sugg') || message.content.toLowerCase().startsWith('<sugg') && message.channel.id != config.suggchannel)
        return message.delete()
    
    if (!message.content.toLowerCase().startsWith(prefix))
        return

    const [cmd, ...args] = message.content
        .slice(prefix.length)
        .trim()
        .split(/ +/g);


    const command = client.commands.get(cmd.toLowerCase()) || client.commands.find(c => c.aliases?.includes(cmd.toLowerCase()))
    if (!command) return;
    await command.run(client, message, args, Discord);

})
