package com.ateam.jjimppong_back.common.entity;

import jakarta.persistence.Column;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.ateam.jjimppong_back.common.dto.request.board.PatchBoardRequestDto;
import com.ateam.jjimppong_back.common.dto.request.board.PostBoardRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "board")
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
    
    @Id
    @Column(name = "board_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardNumber;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_level")
    private Integer userLevel;

    @Column(name = "board_content")
    private String boardContent;

    @Column(name = "board_title")
    private String boardTitle;

    @Column(name = "board_address_category")
    private String boardAddressCategory;

    @Column(name = "board_detail_category")
    private String boardDetailCategory;

    @Column(name = "board_write_date")
    private String boardWriteDate;

    @Column(name = "board_view_count")
    private Integer boardViewCount;

    @Column(name = "board_score")
    private Integer boardScore;

    @Column(name = "board_address")
    private String boardAddress;

    @Column(name = "board_image")
    private String boardImage;

    @Column(name = "text_file_url")
    private String textFileUrl;

    public BoardEntity(PostBoardRequestDto dto, String userId, String userNickname, Integer userLevel) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.userId = userId;
        this.userNickname = userNickname;
        this.userLevel = userLevel;
        this.boardContent = dto.getBoardContent();
        this.boardTitle = dto.getBoardTitle();
        this.boardAddressCategory = dto.getBoardAddressCategory();
        this.boardDetailCategory = dto.getBoardDetailCategory();
        this.boardWriteDate = now.format(dateTimeFormatter);
        this.boardViewCount = 0;
        this.boardScore = 0;
        this.boardAddress = dto.getBoardAddress();
        this.boardImage = dto.getBoardImage();
        this.textFileUrl = dto.getTextFileUrl();
    }
    

    public void patch(PatchBoardRequestDto dto) {
        this.boardAddressCategory = dto.getBoardAddressCategory();
        this.boardDetailCategory = dto.getBoardDetailCategory();
        this.boardTitle = dto.getBoardTitle();
        this.boardContent = dto.getBoardContent();
    }

}

