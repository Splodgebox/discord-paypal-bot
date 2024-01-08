package net.splodgebox.discordpaypalbot;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.splodgebox.discordpaypalbot.commands.VerifyCommand;
import net.splodgebox.discordpaypalbot.config.BotConfig;
import net.splodgebox.discordpaypalbot.data.Role;

import java.util.Map;

public class DiscordPayPalBot {

    @Getter
    private static JDA jda;

    public static void main(String[] args) {
        try {
            jda = JDABuilder.createDefault(BotConfig.getBotToken())
                    .addEventListeners(new VerifyCommand())
                    .build();

            jda.awaitReady();

            jda.getGuilds().forEach(guild -> guild.updateCommands().addCommands(Commands.slash("verify", "Verify purchase of product")
                    .addOption(OptionType.STRING, "transactionid", "Transaction ID of your purchase")
                    .addOptions(getRoleOptions())
                    .addOption(OptionType.ATTACHMENT, "screenshot", "Screenshot of receipt"))
                    .queue());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static OptionData getRoleOptions() {
        Map<String, Role> roles = BotConfig.getRoles();
        OptionData optionData = new OptionData(OptionType.STRING, "role", "Select what role you are verifying for");

        roles.forEach((s, role) -> optionData.addChoice(role.getDisplay(), s));
        return optionData;
    }

}
