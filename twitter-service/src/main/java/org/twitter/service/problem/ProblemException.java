package org.twitter.service.problem;

/**@author someshkumar
 *
 * RFC-7807 compliant problem exception for API.
 */
public class ProblemException extends RuntimeException {
  private ProblemDetail problem;

  private ProblemException(ProblemDetail problem) {
    this.problem = problem;
  }

  public static ProblemException of(ProblemDetail problem) {
    return new ProblemException(problem);
  }

  public ProblemDetail problem() {
    return this.problem;
  }
}
