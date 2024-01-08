package net.splodgebox.discordpaypalbot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.splodgebox.discordpaypalbot.DiscordPayPalBot;
import net.splodgebox.discordpaypalbot.config.BotConfig;
import net.splodgebox.discordpaypalbot.data.Role;

import java.io.IOException;
import java.net.URL;

public class BotLogging {

    public static void sendLogMessage(User user, String transactionId, Role role, Message.Attachment attachment) {
        TextChannel textChannel = DiscordPayPalBot.getJda().getTextChannelById(BotConfig.getLogChannel());

        try {
            URL url = new URL(attachment.getUrl());
            if (textChannel != null) {
                textChannel.sendMessageEmbeds(
                        new EmbedBuilder()
                                .setTitle("User")
                                .setDescription(user.getName() + " - " + user.getIdLong())
                                .addField(new MessageEmbed.Field("TransactionId", transactionId, false))
                                .addField(new MessageEmbed.Field("Role", role.getDisplay(), false))
                                .setImage(url.toString())
                                .build()
                ).queue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
