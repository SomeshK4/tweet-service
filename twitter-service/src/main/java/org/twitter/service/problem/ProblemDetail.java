package org.twitter.service.problem;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/** @author someshkumar */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetail {
  public static final MediaType CONTENT_TYPE = MediaType.APPLICATION_PROBLEM_JSON;

  private URI type;
  private String title;
  private String detail;
  private int status;
  private URI instance;
  private List<InvalidParams> invalidParams;


  private ProblemDetail(Builder builder) {
    this.type = URIEnum.ABOUT_BLANK.uri();
    this.title = builder.status.getReasonPhrase();
    this.detail = builder.detail;
    this.status = builder.status.value();
    this.instance = URI.create("urn:uuid:" + UUID.randomUUID());
    this.invalidParams = builder.invalidParams;

  }


  @RequiredArgsConstructor
  public static class Builder {
    private String detail; // Error Code : Description
    private HttpStatus status;
    private List<InvalidParams> invalidParams;

    public Builder status(@NonNull HttpStatus status) {
      this.status = status;
      return this;
    }

    public Builder detail(@NonNull Throwable th) {
      this.detail = th.getMessage();
      return this;
    }

    public Builder invalidParams(List<InvalidParams> invalidParams) {
      this.invalidParams = invalidParams;
      return this;
    }

    public ProblemDetail build() {
      return new ProblemDetail(this);
    }
  }

  enum URIEnum {
    ABOUT_BLANK {

      @Override
      URI uri() {
        return URI.create("about:blank");
      }
    };

    abstract URI uri();
  }
}
