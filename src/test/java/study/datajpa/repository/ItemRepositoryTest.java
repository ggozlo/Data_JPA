package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;

    @Test
    public void save() {
        Item item = new Item("A");
        itemRepository.save(item);
        // id 를 직접 입력하여 save를 호출한다면 보통 persist가 아니라 merge가 작동하게됨
        // 조회후 입력으로 실행되기에 피해야함
        // persistable 인터페이스를 상속받아 새로운 대상인지 판단을 커스텀 할수 있음
        // 생성 시간의 존재 유무 판단하면 좋다
    }
}