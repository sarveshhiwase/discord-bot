package com.scrumbot.members;

import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MemberManager {
    private Set<Member> currentMembers;

    public MemberManager(){
        currentMembers = new HashSet<>();
    }

    public void addMember(Member member){
        currentMembers.add(member);
    }

    public void deleteMember(Member member){
        currentMembers.remove(member);
    }

    public Set<Member> getCurrentMembers(){
        return currentMembers;
    }
}
