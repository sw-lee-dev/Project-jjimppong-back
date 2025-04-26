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
import com.ateam.jjimppong_back.common.dto.response.board.GetMyBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetRecommandBoardResponseDto;
import com.ateam.jjimppong_back.common.entity.BoardEntity;
import com.ateam.jjimppong_back.common.entity.CommentEntity;
import com.ateam.jjimppong_back.common.entity.GoodEntity;
import com.ateam.jjimppong_back.common.entity.HateEntity;
import com.ateam.jjimppong_back.common.entity.UserEntity;
import com.ateam.jjimppong_back.common.vo.BoardProjection;
import com.ateam.jjimppong_back.common.vo.BoardVO;
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
      // 계정 점수 test 용 게시글 작성 점수 //
      Integer defaultScore = 60;

      BoardEntity boardEntity = new BoardEntity(dto, userId, userNickname, userLevel);
      // 계정 점수 test 용 게시글 점수 추가 작업 //
      boardEntity.setBoardScore(defaultScore);
      boardRepository.save(boardEntity);
      
      // 게시글 작성 시 게시글 작성 점수가 생겨 마이페이지 테이블에 게시글 점수만큼 계정 점수가 수정되고 추가 게시글을 작성하면 합산한 점수를 계정 점수에 수정 - 계정 점수 test 용//
      myPageService.updateMyPageInfo(userId);
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.CREATED);
    
  }

  @Override
  public ResponseEntity<? super GetMyBoardResponseDto> getMyBoard(String userId) {
    
    List<BoardVO> voList = new ArrayList<>();

    try {

      BoardEntity boardEntity = boardRepository.findByUserId(userId);
      String writerId = boardEntity.getUserId();
      UserEntity userEntity = userRepository.findByUserId(writerId);
      Integer userLevel = userEntity.getUserLevel();
      List<BoardProjection> projections = boardRepository.findByUserIdOrderByBoardNumberDesc(userId);

      for (BoardProjection B : projections) {
        BoardVO vo = new BoardVO(
          B.getBoardNumber(),
          B.getBoardContent(),
          B.getBoardTitle(),
          B.getBoardAddressCategory(),
          B.getBoardDetailCategory(),
          B.getBoardWriteDate(),
          B.getBoardViewCount(),
          B.getBoardScore(),
          B.getBoardAddress(),
          B.getBoardImage(),
          B.getUserId(),
          B.getUserNickname(),
          B.setUserLevel(userLevel)
        );
        voList.add(vo);
      }
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetMyBoardResponseDto.success(voList);
    
  }

  @Override
  public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
    
    BoardEntity boardEntity = null;

    try {

      boardEntity = boardRepository.findByBoardNumber(boardNumber);
      if (boardEntity == null) return ResponseDto.noExistBoard();
      
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
        List<RecommandBoardProjection> projections = boardRepository.findAllWithLikeCount();

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

      return ResponseDto.success(HttpStatus.NO_CONTENT);
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  @Override
  public ResponseEntity<? super GetCommentResponseDto> getComment(Integer boardNumber) {
    
    List<CommentEntity> commentEntities = new ArrayList<>();

    try {

      commentEntities = commentRepository.findByBoardNumberOrderByWriteDateDesc(boardNumber);
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetCommentResponseDto.success(commentEntities);

  }

  @Override
  public ResponseEntity<ResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String userId) {
    
    try {

      boolean existBoard = boardRepository.existsByBoardNumber(boardNumber);
      if (!existBoard) return ResponseDto.noExistBoard();

      CommentEntity commentEntity = new CommentEntity(dto, boardNumber, userId);
      commentRepository.save(commentEntity);
      
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

      GoodEntity goodEntity = goodRepository.findByUserIdAndBoardNumber(userId, boardNumber);
      if (goodEntity == null) {
        goodEntity = new GoodEntity(userId, boardNumber);
        goodRepository.save(goodEntity);
      } else {
        goodRepository.delete(goodEntity);
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

      HateEntity hateEntity = hateRepository.findByUserIdAndBoardNumber(userId, boardNumber);
      if (hateEntity == null) {
        hateEntity = new HateEntity(userId, boardNumber);
        hateRepository.save(hateEntity);
      } else {
        hateRepository.delete(hateEntity);
      }

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);
  }

  
  
}
