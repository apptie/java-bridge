# 미션 - 다리 건너기

## 리펙토링 목록

- `PlayerState`와 `Bridge`간 순환 참조 제거
- `GameCommand`에서 사용하고 있던 잘못된 이름 수정
- 해당 클래스에서 사용하는 상수 및 메세지를 모두 내부에서 관리
- 불필요한 `GameStatus` 일부 상태 삭제
- `GuideView`에서 출력하는 메세지를 `OutputView`로 통합