package ch.heigvd.wordoff.server.Model.Modes;

import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Invitation;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Mode {
    @Id
    private Long id;

    @OneToMany(mappedBy = "pk.mode")
    @MapKey(name = "pk.target")
    private Map<Long, Invitation> invitations;

    @OneToMany(mappedBy = "mode")
    private List<Game> games;

    private Date startDate;

    public Mode() {
        this.invitations = new TreeMap<>();
        this.games = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(Map<Long, Invitation> invitations) {
        this.invitations = invitations;
    }

    public void putInvitation(Invitation invit) {
        invitations.put(invit.getTarget().getId(), invit);
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}