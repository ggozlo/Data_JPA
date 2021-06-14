package study.datajpa.repository;

public class UsernameOnlyDto { // 클래스 기반의 프로젝션

    private final String username;

    public UsernameOnlyDto(String username) { // 생성자에서 파라메터 명의 이름이 맞게 주입
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
