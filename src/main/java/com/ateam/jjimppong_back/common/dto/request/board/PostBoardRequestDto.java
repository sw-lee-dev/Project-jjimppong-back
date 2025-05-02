package com.ateam.jjimppong_back.common.dto.request.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostBoardRequestDto {
  @NotBlank
  private String boardAddressCategory;
  @NotNull
  private String boardDetailCategory;
  @NotBlank
  private String boardTitle;
  @NotBlank
  private String boardContent;
  private String boardAddress;
  private String boardImage;

  private String textFileUrl;
}
