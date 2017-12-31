package ch.heigvd.wordoff.server.Model.Modes;

import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.User;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Entity
public class DuelMode extends Mode {

    public DuelMode() {}

    public DuelMode(User participant) {
        putInvitation(new Invitation(this, participant, InvitationStatus.ORIGIN, "NO ADVERSARY YET"));
    }

    public DuelMode(List<User> participants) {
        User origin = participants.get(0);
        User adversary = participants.get(1);
        putInvitation(new Invitation(this, origin, InvitationStatus.ORIGIN, adversary.getName()));
        putInvitation(new Invitation(this, adversary, InvitationStatus.WAITING, origin.getName()));
    }

    public Game getGame() {
        return getGames().get(0);
    }

    @Override
    public boolean isEnded() {
        return getGame().isEnded();
    }
}
