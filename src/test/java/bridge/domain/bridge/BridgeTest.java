package bridge.domain.bridge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import bridge.BridgeNumberGenerator;
import bridge.BridgeRandomNumberGenerator;
import bridge.domain.bridge.exception.WrongGeneratorException;
import bridge.domain.player.PlayerState;
import bridge.helper.common.CommonStubBridgeGeneratorField;
import bridge.helper.stub.StubBridgeNumberGenerator;
import bridge.helper.stub.StubPlayerState;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BridgeTest {

    @Nested
    @DisplayName("int size, BridgeNumberGenerator generator를 매개변수로 받는 생성자는")
    class DescribeIntGeneratorConstructorTest {

        private final int defaultSize = 3;
        private final BridgeNumberGenerator validGenerator = new BridgeRandomNumberGenerator();

        @Nested
        @DisplayName("만약 유효한 다리의 크기와 다리를 생성하는 전략이 주어지면")
        class ContextWithValidSizeAndGeneratorTest {

            @Test
            @DisplayName("다리를 생성해 Bridge를 초기화하고 반환한다.")
            void it_returns_bridge() {
                assertThatCode(() -> new Bridge(defaultSize, validGenerator))
                    .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("만약 유효한 다리의 크기와 유효하지 않은 다리를 생성하는 전략이 주어지면")
        class ContextWithSizeAndInvalidGeneratorTest {

            @Test
            @DisplayName("WrongGeneratorException 예외가 발생한다")
            void it_throws_exception() {
                BridgeNumberGenerator invalidGenerator = new StubBridgeNumberGenerator(List.of(2, 3, 4));

                assertThatThrownBy(() -> new Bridge(defaultSize, invalidGenerator))
                        .isInstanceOf(WrongGeneratorException.class);
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 다리의 크기와 유효한 다리를 생성하는 전략이 주어지면")
        class ContextWithInvalidSizeAndGeneratorTest {

            @ParameterizedTest
            @ValueSource(ints = {-1, 0, 1, 2, 21})
            @DisplayName("IllegalArgumentException 예외가 발생한다")
            void it_throws_exception(int size) {
                assertThatThrownBy(() -> new Bridge(size, validGenerator))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    @DisplayName("calculatePlayerMoving 메소드는")
    class DescribeCalculatePlayerMovingMethodTest extends CommonBridgeField {

        @Nested
        @DisplayName("만약 플레이어가 이동할 다리 방향과 플레이어의 상태 정보가 주어지면")
        class ContextWithBridgeTileAndPlayerPositionTest {

            @ParameterizedTest
            @CsvSource(
                    value = {
                        "UP:false",
                        "DOWN:true"
                    },
                    delimiter = ':'
            )
            @DisplayName("건널 수 있는지 여부를 반환한다")
            void it_returns_movable(BridgeTile playerStep, boolean expected) {
                PlayerState playerState = new StubPlayerState(1);
                boolean actual = bridge.calculatePlayerMoving(playerStep, playerState);

                assertThat(actual).isSameAs(expected);
            }
        }
    }

    @Nested
    @DisplayName("findEndOfBridgePosition 메소드는")
    class DescribeFindEndOfBridgePositionMethodTest extends CommonBridgeField {

        @Nested
        @DisplayName("만약 호출하면")
        class ContextWithPlayerPositionTest {

            @Test
            @DisplayName("다리의 마지막 칸 위치를 반환한다")
            void it_returns_endOfBridgePosition() {
                int actual = bridge.findEndOfBridgePosition();

                assertThat(actual).isSameAs(3);
            }
        }
    }

    private static abstract class CommonBridgeField extends CommonStubBridgeGeneratorField {

        protected Bridge bridge = new Bridge(3, stubGenerator);
    }
}