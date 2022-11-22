package bridge.domain.player;

import static org.assertj.core.api.Assertions.assertThat;

import bridge.domain.game.BridgeTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlayerStepHistoryTest {

    private PlayerStepHistory playerStepHistory;

    @BeforeEach
    void initPlayerStepHistory() {
        playerStepHistory = new PlayerStepHistory();
        playerStepHistory.updateHistory(BridgeTile.DOWN, true);
        playerStepHistory.updateHistory(BridgeTile.UP, false);
    }

    @Nested
    @DisplayName("getPlayerTargetTileHistory 메소드는")
    class DescribeGetPlayerTargetTileHistoryMethodTest {

        @Nested
        @DisplayName("만약 BridgeTile이 주어지면")
        class ContextWithBridgeTileTest {

            @Test
            @DisplayName("BridgeTile에 맞는 플레이어의 이동 경로를 반환한다")
            void it_return_targetHistory() {
                String upHistory = playerStepHistory.getPlayerTargetTileHistory(BridgeTile.UP);
                String downHistory = playerStepHistory.getPlayerTargetTileHistory(BridgeTile.DOWN);

                assertThat(upHistory).contains("X");
                assertThat(downHistory).contains("O");
            }
        }
    }

    @Nested
    @DisplayName("clear 메소드는")
    class DescribeClearMethodTest {

        @Nested
        @DisplayName("만약 호출되면")
        class ContextWithoutParameterTest {

            @Test
            @DisplayName("플레이어 이동 경로를 초기화한다")
            void it_clear_history() {
                String downHistory = playerStepHistory.getPlayerTargetTileHistory(BridgeTile.DOWN);
                assertThat(downHistory.length()).isGreaterThan(0);

                playerStepHistory.clear();

                downHistory = playerStepHistory.getPlayerTargetTileHistory(BridgeTile.DOWN);
                assertThat(downHistory.length()).isSameAs(0);
            }
        }
    }
}