package itgenio.library.repository;

import itgenio.library.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    List<Reader> findAllByLastname(String lastname);

    List<Reader> findAllByName(String name);

    Reader findByPhone(String phone);

    void deleteByPhone(String phone);
}
