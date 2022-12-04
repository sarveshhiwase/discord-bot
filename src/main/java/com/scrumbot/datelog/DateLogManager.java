package com.scrumbot.datelog;

import net.dv8tion.jda.api.entities.Member;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class DateLogManager implements Serializable {
    private Date date;

    public DateLogManager(Date date){
        this.date = date;
    }
    private Set<Member> morningMembers,nightMembers;

    public void setMorningMembers(Set<Member> morningMembers){
        this.morningMembers = morningMembers;
    }

    public void setNightMembers(Set<Member> nightMembers){
        this.nightMembers = nightMembers;
    }

    public Set<Member> getMorningMembers() {
        return morningMembers;
    }

    public Set<Member> getNightMembers() {
        return nightMembers;
    }
}
