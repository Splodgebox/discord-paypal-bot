package net.splodgebox.discordpaypalbot.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.splodgebox.discordpaypalbot.api.PayPalApiClient;
import net.splodgebox.discordpaypalbot.config.BotConfig;
import net.splodgebox.discordpaypalbot.data.Role;
import net.splodgebox.discordpaypalbot.utils.BotLogging;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class VerifyCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("verify")) return;

        String transactionId;
        Role role;
        Message.Attachment attachment;

        try {
            transactionId = event.getOption("transactionid").getAsString();
            role = BotConfig.getRole(event.getOption("role").getAsString());
            attachment = event.getOption("screenshot").getAsAttachment();
        } catch (NullPointerException exception) {
            event.reply("Invalid or missing arguments for this command!").queue();
            return;
        }

        PayPalApiClient payPalApiClient = new PayPalApiClient();
        boolean verified = payPalApiClient.isValidPurchase(transactionId);

        if (verified) {
            try {
                BotLogging.sendLogMessage(event.getUser(), transactionId, role, attachment);
                event.reply("You have been verified for " + role.getDisplay() + "!").queue();
                addRoleToUser(event.getGuild(), event.getUser(), role);
            } catch (NullPointerException exception) {
                event.reply("Failed to verify!").queue();
            }
        } else {
            event.reply("Failed to verify!").queue();
        }
    }

    public void addRoleToUser(@Nullable Guild guild, @Nullable User user, Role role) {
        if (guild == null || user == null) {
            throw new NullPointerException();
        }

        guild.addRoleToMember(user, Objects.requireNonNull(guild.getRoleById(role.getId()))).queue();
    }
}
