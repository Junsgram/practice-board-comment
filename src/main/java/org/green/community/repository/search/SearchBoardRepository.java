package org.green.community.repository.search;

import org.green.community.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public interface SearchBoardRepository{
    Board search1();
    Board search2();
    Board search3();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);

}
