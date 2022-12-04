import com.scrumbot.event.AllEventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;

public class ScrumBot {
    private Dotenv config;
    private ShardManager shardManager;

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public void start(){
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.enableCache(EnumSet.allOf(CacheFlag.class));
        builder.enableIntents(EnumSet.allOf(GatewayIntent.class));
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);

        builder.addEventListeners(new AllEventListener());
        shardManager = builder.build();
    }

    public static void main(String[] args) {
        try {
            ScrumBot scrumBot = new ScrumBot();
            scrumBot.start();
        } catch (Exception e) {
            System.out.println("Please reauthorize using new token, or check your token");
        }

    }
}
