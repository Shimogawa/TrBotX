package academy.littlewitch.bot.commands.jinyan;

public class VoteStatus {

    private long voteStartTime;

    private int votes;

    public VoteStatus(long voteStartTime) {
        this.voteStartTime = voteStartTime;
    }

    public long getVoteStartTime() {
        return voteStartTime;
    }

    public int getVotes() {
        return votes;
    }

    public void addOneVote() {
        this.votes++;
    }

    public void clearVotes() {
        this.votes = 0;
    }
}
