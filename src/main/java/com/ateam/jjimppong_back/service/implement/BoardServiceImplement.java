package com.ateam.jjimppong_back.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ateam.jjimppong_back.common.dto.request.board.PatchBoardRequestDto;
import com.ateam.jjimppong_back.common.dto.request.board.PostBoardRequestDto;
import com.ateam.jjimppong_back.common.dto.request.board.PostCommentRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetCommentResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetFilteredBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetGoodResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetHateResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetRecommandBoardResponseDto;
import com.ateam.jjimppong_back.common.entity.BoardEntity;
import com.ateam.jjimppong_back.common.entity.CommentEntity;
import com.ateam.jjimppong_back.common.entity.GoodEntity;
import com.ateam.jjimppong_back.common.entity.HateEntity;
import com.ateam.jjimppong_back.common.entity.UserEntity;
import com.ateam.jjimppong_back.common.vo.CommentProjection;
import com.ateam.jjimppong_back.common.vo.CommentVO;
import com.ateam.jjimppong_back.common.vo.FilteredBoardProjection;
import com.ateam.jjimppong_back.common.vo.FilteredBoardVO;
import com.ateam.jjimppong_back.common.vo.RecommandBoardProjection;
import com.ateam.jjimppong_back.common.vo.RecommandBoardVO;
import com.ateam.jjimppong_back.repository.BoardRepository;
import com.ateam.jjimppong_back.repository.CommentRepository;
import com.ateam.jjimppong_back.repository.GoodRepository;
import com.ateam.jjimppong_back.repository.HateRepository;
import com.ateam.jjimppong_back.repository.UserRepository;
import com.ateam.jjimppong_back.service.BoardService;
import com.ateam.jjimppong_back.service.MyPageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final CommentRepository commentRepository; 
  private final GoodRepository goodRepository;
  private final HateRepository hateRepository;
  private final MyPageService myPageService;

  @Override
  public ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId) {
    
    try {

      UserEntity userEntity = userRepository.findByUserId(userId);
      String userNickname = userEntity.getUserNickname();
      Integer userLevel = userEntity.getUserLevel();

          BoardEntity boardEntity = new BoardEntity(dto, userId, userNickname, userLevel);

          // ✅ textFileUrl이 DTO에 포함되어 있다면 Entity에도 반영
          boardEntity.setTextFileUrl(dto.getTextFileUrl());

          boardRepository.save(boardEntity);

          // ✅ 마이페이지 점수 업데이트 로직
          myPageService.updateMyPageInfo(userId);

      } catch (Exception exception) {
          exception.printStackTrace();
          return ResponseDto.databaseError();
      }

      return ResponseDto.success(HttpStatus.CREATED);
  }

  // @Override
  // public ResponseEntity<? super GetMyBoardResponseDto> getMyBoard(String userId) {
    
  //   List<BoardVO> voList = new ArrayList<>();

  //   try {

  //     List<BoardProjection> projections = boardRepository.findByUserIdOrderByBoardNumberDesc(userId);

  //     for (BoardProjection B : projections) {
  //       BoardVO vo = new BoardVO(
  //         B.getBoardNumber(),
  //         B.getBoardContent(),
  //         B.getBoardTitle(),
  //         B.getBoardAddressCategory(),
  //         B.getBoardDetailCategory(),
  //         B.getBoardWriteDate(),
  //         B.getBoardViewCount(),
  //         B.getBoardScore(),
  //         B.getBoardAddress(),
  //         B.getBoardImage(),
  //         B.getUserId(),
  //         B.getUserNickname(),
  //         B.getUserLevel()
  //       );
  //       voList.add(vo);
  //     }
      
  //   } catch (Exception exception) {
  //     exception.printStackTrace();
  //     return ResponseDto.databaseError();
  //   }

  //   return GetMyBoardResponseDto.success(voList);
    
  // }

  @Override
  public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
    
    BoardEntity boardEntity = null;

    try {

      boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return ResponseDto.noExistBoard();

      // 게시글 상세 보기를 요청했을때 유저 정보에 저장된 userLevel값이 보이도록 작업
      String writerId = boardEntity.getUserId();
      UserEntity userEntity = userRepository.findByUserId(writerId);
      Integer userLevel = userEntity.getUserLevel();
      Integer boardUserLevel = boardEntity.getUserLevel();
      boolean isMatch = (boardUserLevel == userLevel);
      if (!isMatch) {
        boardUserLevel = userLevel;
        boardEntity.setUserLevel(boardUserLevel);
      }
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetBoardResponseDto.success(boardEntity);

  }

  // 추천 게시물 목록 가져오기 //
  @Override
  public ResponseEntity<? super GetRecommandBoardResponseDto> getRecommandBoard() {
    List<RecommandBoardVO> voList = new ArrayList<>();

    try {
        List<RecommandBoardProjection> projections = boardRepository.findAllWithGoodCount();

        for (RecommandBoardProjection p : projections) {
            RecommandBoardVO vo = new RecommandBoardVO(
                p.getBoardNumber(),
                p.getBoardWriteDate(),
                p.getBoardAddressCategory(),
                p.getBoardDetailCategory(),
                p.getBoardTitle(),
                p.getBoardViewCount(),
                p.getBoardScore(),
                p.getBoardImage(),
                p.getUserNickname(),
                p.getGoodCount()
            );
            voList.add(vo);
        }

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseDto.databaseError();
    }

    return GetRecommandBoardResponseDto.success(voList);
  }

  @Override
  public ResponseEntity<? super GetFilteredBoardResponseDto> getFilteredBoardWriteDate() {

        List<FilteredBoardVO> voList = new ArrayList<>();

        try {

          List<FilteredBoardProjection> projections = boardRepository.findAllWithOrderByWriteDateDesc();
          for (FilteredBoardProjection p : projections){
            FilteredBoardVO vo = new FilteredBoardVO(
              p.getBoardNumber(),
              p.getBoardWriteDate(),
              p.getBoardAddressCategory(),
              p.getBoardDetailCategory(),
              p.getBoardTitle(),
              p.getBoardViewCount(),
              p.getBoardScore(),
              p.getBoardImage(),
              p.getUserNickname(),
              p.getUserLevel(),
              p.getGoodCount(),
              p.getCommentCount()
            );
            voList.add(vo);
          }
          
        } catch (Exception exception) {
          exception.printStackTrace();
          return ResponseDto.databaseError();
        }

        return GetFilteredBoardResponseDto.success(voList);
  }

  
  @Override
  public ResponseEntity<? super GetFilteredBoardResponseDto> getFilteredBoardViewCount() {
    List<FilteredBoardVO> voList = new ArrayList<>();

        try {

          List<FilteredBoardProjection> projections = boardRepository.findAllWithOrderByViewCountDesc();
          for (FilteredBoardProjection p : projections){
            FilteredBoardVO vo = new FilteredBoardVO(
              p.getBoardNumber(),
              p.getBoardWriteDate(),
              p.getBoardAddressCategory(),
              p.getBoardDetailCategory(),
              p.getBoardTitle(),
              p.getBoardViewCount(),
              p.getBoardScore(),
              p.getBoardImage(),
              p.getUserNickname(),
              p.getUserLevel(),
              p.getGoodCount(),
              p.getCommentCount()
            );
            voList.add(vo);
          }
          
        } catch (Exception exception) {
          exception.printStackTrace();
          return ResponseDto.databaseError();
        }

        return GetFilteredBoardResponseDto.success(voList);
  }

  @Override
  public ResponseEntity<? super GetFilteredBoardResponseDto> getFilteredBoardGoodCount() {
    List<FilteredBoardVO> voList = new ArrayList<>();

        try {

          List<FilteredBoardProjection> projections = boardRepository.findAllWithOrderByGoodCountDesc();
          for (FilteredBoardProjection p : projections){
            FilteredBoardVO vo = new FilteredBoardVO(
              p.getBoardNumber(),
              p.getBoardWriteDate(),
              p.getBoardAddressCategory(),
              p.getBoardDetailCategory(),
              p.getBoardTitle(),
              p.getBoardViewCount(),
              p.getBoardScore(),
              p.getBoardImage(),
              p.getUserNickname(),
              p.getUserLevel(),
              p.getGoodCount(),
              p.getCommentCount()
            );
            voList.add(vo);
          }
          
        } catch (Exception exception) {
          exception.printStackTrace();
          return ResponseDto.databaseError();
        }

        return GetFilteredBoardResponseDto.success(voList);
  }




  @Override
  public ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String userId) {
    
    try {

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return ResponseDto.noExistBoard();

      String boardWriterID = boardEntity.getUserId();
      boolean isWriter = boardWriterID.equals(userId);
      if (!isWriter) return ResponseDto.noPermission();

      boardEntity.patch(dto);
      boardRepository.save(boardEntity);
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);

  }

  @Override
  public ResponseEntity<ResponseDto> deleteBoard(Integer boardNumber, String userId) {

    try {

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return ResponseDto.noExistBoard();

      String boardWriterId = boardEntity.getUserId();
      boolean isWriter = boardWriterId.equals(userId);
      if (!isWriter) return ResponseDto.noPermission();

      boardRepository.delete(boardEntity);

      // 게시글이 삭제되면 해당 게시글의 점수가 계정 점수에서 마이너스
      myPageService.updateMyPageInfo(userId);
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<? super GetCommentResponseDto> getComment(Integer boardNumber) {
    
    List<CommentVO> voList = new ArrayList<>();

    try {

        List<CommentProjection> projections = commentRepository.findAllByBoardNumberOrderByCommentWriteDateDesc(boardNumber);
        for (CommentProjection p : projections){
          CommentVO vo = new CommentVO(
            p.getCommentNumber(), 
            p.getCommentWriteDate(), 
            p.getCommentContent(), 
            p.getCommentWriterId(), 
            p.getUserNickname(), 
            p.getUserLevel()
            );
            voList.add(vo);
        }
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetCommentResponseDto.success(voList);

  }

  @Override
  public ResponseEntity<ResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String userId) {
    
    try {

      boolean existBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!existBoard) return ResponseDto.noExistBoard();

      CommentEntity commentEntity = new CommentEntity(dto, boardNumber, userId);
      commentRepository.save(commentEntity);
      // 댓글 작성 게시글 점수 수정
      putBoardScore(boardNumber);
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.CREATED);

  }

  // 좋아요, 싫어요
  @Override
  public ResponseEntity<? super GetGoodResponseDto> getGood(Integer boardNumber) {
    List<GoodEntity> goodEntities = new ArrayList<>();
    
    try {
      
      goodEntities = goodRepository.findByBoardNumber(boardNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetGoodResponseDto.success(goodEntities);
  }

  @Override
  public ResponseEntity<ResponseDto> putGood(Integer boardNumber, String userId) {
    try {
      
      boolean isExistBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!isExistBoard) return ResponseDto.noExistBoard();

      GoodEntity goodEntity = goodRepository.findByUserIdAndBoardNumber(userId, boardNumber);
      if (goodEntity == null) {
        goodEntity = new GoodEntity(userId, boardNumber);
        goodRepository.save(goodEntity);
        // 좋아요 점수 수정
        putBoardScore(boardNumber);
      } else {
        goodRepository.delete(goodEntity);
        // 좋아요 점수 수정
        putBoardScore(boardNumber);
      }

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<? super GetHateResponseDto> getHate(Integer boardNumber) {
    List<HateEntity> hateEntities = new ArrayList<>();
    
    try {
      
      hateEntities = hateRepository.findByBoardNumber(boardNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetHateResponseDto.success(hateEntities);
  }

  @Override
  public ResponseEntity<ResponseDto> putHate(Integer boardNumber, String userId) {
    try {

      boolean isExistBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!isExistBoard) return ResponseDto.noExistBoard();

      HateEntity hateEntity = hateRepository.findByUserIdAndBoardNumber(userId, boardNumber);
      if (hateEntity == null) {
        hateEntity = new HateEntity(userId, boardNumber);
        hateRepository.save(hateEntity);
        // 싫어요 게시글 점수 수정
        putBoardScore(boardNumber);
      } else {
        hateRepository.delete(hateEntity);
        // 싫어요 게시글 점수 수정
        putBoardScore(boardNumber);
      }

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ResponseDto> deleteComment(Integer commentNumber, String userId) {
    try {
      
      CommentEntity commentEntity = commentRepository.findByCommentNumber(commentNumber);
      if (commentEntity == null) return ResponseDto.noExistComment();

      String commentWriterId = commentEntity.getCommentWriterId();
      boolean isWriter = commentWriterId.equals(userId);
      if (!isWriter) return ResponseDto.noPermission();

      // 게시글 점수 수정을 위해 댓글이 삭제될 게시글 번호 불러오기
      Integer boardNumber = commentEntity.getBoardNumber();
      
      commentRepository.delete(commentEntity);
      // 댓글 삭제되면 게시글 점수 수정
      putBoardScore(boardNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.OK);
  }

  // 조회수 증가
  @Override
  public ResponseEntity<ResponseDto> putViewCount(Integer boardNumber) {
    try {

      boolean isExistBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!isExistBoard) return ResponseDto.noExistBoard();

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      boardEntity.setBoardViewCount(boardEntity.getBoardViewCount() + 1);

      boardRepository.save(boardEntity);
      // 조회수에 따른 게시글 점수 수정
      putBoardScore(boardNumber);
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.OK);
  }

  // board_score 계산
  @Override
  public ResponseEntity<ResponseDto> putBoardScore(Integer boardNumber) {
    
    try {

      boolean isExistBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!isExistBoard) return ResponseDto.noExistBoard();

      BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
      Integer totalScore = boardRepository.calculateBoardScore(boardNumber);
      boardEntity.setBoardScore(totalScore);

      boardRepository.save(boardEntity);

      // 게시글 점수에 따라 합산 점수가 마이페이지에 유저 점수로 입력됨
      String userId = boardEntity.getUserId();
      myPageService.updateMyPageInfo(userId);
      

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);
  }
  
}