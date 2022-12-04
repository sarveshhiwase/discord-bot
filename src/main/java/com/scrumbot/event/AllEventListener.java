package com.scrumbot.event;

import com.scrumbot.datelog.DateLogManager;
import com.scrumbot.filedatabase.Database;
import com.scrumbot.members.MemberManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.scrumbot.filedatabase.Database.*;

public class AllEventListener extends ListenerAdapter {
    MemberManager memberManager;
    public AllEventListener() {
        memberManager = new MemberManager();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equalsIgnoreCase("add")) {
            OptionMapping option = event.getOption("user");
            if(option != null) {
                Member member = option.getAsMember();
                memberManager.addMember(member);
                event.reply(member.getUser().getAsMention() + " added to the current members list").queue();
            }
        }
        if (command.equalsIgnoreCase("delete")) {
            OptionMapping option = event.getOption("user");
            if(option != null){
                Member member = option.getAsMember();
                memberManager.deleteMember(member);
                event.reply(member.getUser().getAsMention() + " deleted from the current members list").queue();
            }
        }
        if (command.equalsIgnoreCase("list")) {
            StringBuilder membersList = new StringBuilder();
            for(Member member: memberManager.getCurrentMembers()){
                membersList.append(member.getUser().getAsMention() + "\n");
            }
            String messageListToSend = membersList.toString();
            if(messageListToSend != null && !messageListToSend.isEmpty())
                event.reply(messageListToSend).queue();
            else
                event.reply("Empty list, add new members to the food list").queue();
        }
        if (command.equalsIgnoreCase("log")) {
            OptionMapping option = event.getOption("date");
            if(option != null) {
                String strDate = option.getAsString();
                Date date;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
                } catch (Exception e) {
                    event.reply("Please enter a valid date like this format - 03/12/2022").queue();
                    return;
                }
                DateLogManager dateLogManager = new DateLogManager(date);
                StringBuilder dateLogStr = new StringBuilder();
                dateLogStr.append("Morning Members - \n");
                dateLogStr.append(dateLogManager.getMorningMembers());
                dateLogStr.append("\n");
                dateLogStr.append("Night Members - \n");
                dateLogStr.append(dateLogManager.getNightMembers());

                if (!dateLogStr.toString().isEmpty())
                    event.reply(dateLogStr.toString()).queue();
            } else event.reply("please add the date.").queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        List<Member> members = event.getGuild().getMembers();
        StringBuilder currentMembers = new StringBuilder();
        for (Member member : members) {
            currentMembers.append("**").append(member.getUser().getAsMention()).append("**");
            currentMembers.append("\n");
        }
        String membersString = currentMembers.toString();
        event.getChannel().sendMessage(membersString).queue();
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        System.out.println(event.getMessage().getContentRaw());
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandsList = new ArrayList<>();
        commandsList.add(Commands.slash("add", "Adds member to current observable member list").addOption(OptionType.USER, "user", "current user in your server", true));
        commandsList.add(Commands.slash("delete", "Deletes member from current observable member list").addOption(OptionType.USER, "user", "current user in your server", true));
        commandsList.add(Commands.slash("list", "lists members from current observable member list"));
        commandsList.add(Commands.slash("log", "logs the members from observable member list on specified date").addOption(OptionType.STRING,"date","Date for which you want the logs for",true));
        event.getGuild().updateCommands().addCommands(commandsList).queue();
    }
}
