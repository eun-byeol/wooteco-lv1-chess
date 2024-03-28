# java-chess

체스 미션 저장소

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

# 용어집

- 기물(Piece) : 체스판의 말

# 기능 목록

## 체스판 초기화

- [x] 8*8 크기의 체스판을 만든다
    - 열은 왼쪽부터 a ~ h이고, 행은 아래부터 위로 1 ~ 8로 설정한다
- [x] 기물을 배치한다
    - [x] 1,2행에는 백 진영을 놓는다
  ```
  pppppppp
  rnbqkbnr
  ```
    - [x] 7,8행에는 흑 진영을 놓는다
  ```
  RNBQKBNR
  PPPPPPPP
  ```

## 체스 이동

- source위치에서 target위치로 기물을 이동한다
- 각 위치가 유효한지 확인
    - [x] 체스판 범위를 벗어나는지 확인
    - [x] 출발 좌표에 말이 있는지 확인
    - [x] 도착 좌표에 같은 진영의 말이 있는지 확인

- 이동 조건
    - [x] 출발지점에 있는 기물이 이동이 가능한지 확인
    - [x] 도착 지점까지 다른 기물이 있는 지 확인(막혀있으면 이동 불가)
        - 나이트는 체크하지 않음
    - [x] 자신의 차례인지 확인(짝수 - 백 / 홀수 - 흑)

- 이동 수행
    - [x] 도착 좌표로 기물을 이동하고, 출발 좌표를 NONE으로 만든다

- 이동 종료
    - [x] King이 잡히면 게임을 종료한다

### 체스 이동 규칙

- [x] 폰(pawn)
    - [x] 전진 1칸
    - [x] 한 번도 안 움직였을 때는 2칸 이동 가능
    - [x] attack 시에는 전방 대각선 1칸 이동 가능
- [x] 룩(rook)
    - 상하좌우 원하는 칸만큼 이동
- [x] 나이트(knight)
    - 두 칸 전진 후 90도 방향으로 1칸 전진 가능(L자 모양으로 이동 가능)
    - 타 기물 통과 가능
- [x] 비숍(bishop)
    - 대각선 원하는 칸만큼 이동
- [x] 퀸(queen)
    - 상하좌우 대각선 원하는 칸만큼 이동
- [x] 킹(king)
    - 상하좌우 대각선 1칸 이동

## 승패

### 승리 조건

- [ ] 상대의 King을 잡는 경우
- [ ] King이 잡히지 않고 총 점수가 높은 경우

## 점수 계산

- [x] 현재 남아 있는 말에 대한 점수를 구한다
- [x] 한 번에 한 쪽의 점수만 계산한다

## 기물별 점수

- [x] pawn : 1점 or 같은 세로줄에 같은 색의 폰이 있는 경우 0.5점
- [x] knight : 2.5점
- [x] bishop : 3점
- [x] rook : 5점
- [x] queen : 9점
- [x] king : 0점
- [x] none : 0점

## 게임 명령어

- [x] start이면 체스 게임을 시작한다
- [x] end이면 체스 게임을 종료한다
- [x] move이면 체스를 이동한다
- [ ] status이면 승패와 점수를 계산한다

## 입력

- [x] 게임 명령어를 입력한다
    - [x] move 명령어는 출발 위치와 도착 위치를 함께 입력한다

## 출력

- [x] 체스판을 출력한다
- [x] 진영 별 점수를 출력한다
- [ ] 승패 결과를 출력한다
