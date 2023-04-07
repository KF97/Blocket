## develop 브랜치 생성
![1](/uploads/00aeb24532283d7ee9a5b7bf378bf694/1.PNG)
- 반드시 feature 브랜치는 develop에서 생성해야함!

## 작업 후에 커밋하는 과정
작업할 브랜치 이름은 최대한 명시적, 간단하게 만들기! 
소문자와 언더바만 사용! (`feature/user_create`)

## 작업 전
![2](/uploads/0337468028c4b83a003263f249509ce6/2.PNG)
1. 내가 작업할 브랜치 만들기

    `git branch <브랜치 이름>`

1. 작업할 브랜치로 이동하기

    `git checkout <브랜치 이름>`

## 작업 후
![3](/uploads/235a41330c66288b4c749eb76b203d90/3.PNG)
1. 작업 완료하고 브랜치 확인하기

    **현재 나의 터미널 브랜치가 `feature/example` 인지 확인!**

2. 커밋 메세지 날리기

    `git add .`

    `git commit -m <commit msg>`

3. `develop` 브렌치 상태 최신화 시키기

    `git checkout develop`

    `git pull origin develop`

4. 내가 작업한 브랜치로 돌아와 내용 `push` 하기

    `git checkout <feature/example>` 

    `git push origin <feature/example>`

5. GitLab에 접속해서 MergeRequest 날리기

    **MR 할때 Target Branch가 develop 브랜치인지 꼭 확인!**

    컨플릭트가 발생하면,,, 잠시 대기.... Help 요청...

    **절대로 GitLab 원격 Repo에서 MR Confilct 해결하지 말고
    해결해야 하는 부분확인하고
    로컬에서 수정해서
    MR 취소하고 로컬에서 다시 push 하기!**

## 브랜치 생성이 안되는 경우!
- 생성 후 바로 push 하고 확인해보기
