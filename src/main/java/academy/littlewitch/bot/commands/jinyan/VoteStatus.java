package academy.littlewitch.bot.commands.jinyan;

import java.util.HashSet;

public class VoteStatus {

    private long voteStartTime;

    private int votes;

    private HashSet<Long> votedUsers;

    public VoteStatus(long voteStartTime, long initializer) {
        this.voteStartTime = voteStartTime;
        votes = 1;
        votedUsers = new HashSet<>();
        votedUsers.add(initializer);
    }

    public long getVoteStartTime() {
        return voteStartTime;
    }

    public int getVotes() {
        return votes;
    }

    public HashSet<Long> getVotedUsers() {
        return votedUsers;
    }

    /**
     * Check and vote for the user.
     * @param voter the user who votes
     * @return <code>true</code> if the user haven't voted and now successfully voted,
     * <code>false</code> otherwise.
     */
    public boolean addVote(long voter) {
        if (votedUsers.contains(voter)) {
            return false;
        }
        this.votes++;
        this.votedUsers.add(voter);
        return true;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void clearVotes() {
        this.votes = 0;
        this.votedUsers.clear();
    }
}
