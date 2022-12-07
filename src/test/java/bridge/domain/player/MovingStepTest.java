package bridge.domain.player;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MovingStepTest {

    @Nested
    @DisplayName("findMovingStep 메소드는")
    class DescribeFindMovingStepMethodTest {

        @Nested
        @DisplayName("만약 기록해야 할 다리 경로인지 여부 isTargetBridgeTile과 플레이어가 움직였는지 여부 movable이 주어지면")
        class ContextWithIsTargetBridgeTileAndMovableTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "false:false:' '",
                        "true:false:X",
                        "true:true:O"
                    },
                    delimiter = ':'
            )
            @DisplayName("플레이어의 이동 경로에 맞는 메세지를 반환한다")
            void it_returns_moveMessage(boolean isTargetBridgeTile, boolean movable, String expected) {
                MovingStep actual = MovingStep.findMovingStep(isTargetBridgeTile, movable);

                assertThat(actual.toString()).contains(expected);
            }
        }
    }

    @Nested
    @DisplayName("toString 메소드는")
    class DescribeToStringMethodTest {

        @Nested
        @DisplayName("만약 호출하면")
        class ContextWithoutParameterTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "CORRECT_MOVE:O",
                        "WRONG_MOVE:X",
                        "NOT_MOVE:' '"
                    },
                    delimiter = ':'
            )
            @DisplayName("이동 결과에 따른 메세지를 반환한다")
            void it_returns_message(MovingStep movingStep, String expected) {
                assertThat(movingStep.toString()).contains(expected);
            }
        }
    }
}