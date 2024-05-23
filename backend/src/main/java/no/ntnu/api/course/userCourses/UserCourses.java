package no.ntnu.api.course.userCourses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.api.course.Course;

@Entity
@Table(name = "user_courses")
public class UserCourses {

  @Id
  @Schema(description = "The user course id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_course_id")
  private int userCourseId;

  @Schema(description = "The user id")
  @Column(name = "user_id")
  private int userId;

  @Schema(description = "The course id")
  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  public UserCourses() {

  }

  public void setUserCourseId(int userCourseId) {
    this.userCourseId = userCourseId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  @JsonIgnore
  public int getUserCourseId() {
    return userCourseId;
  }

  public int getUserId() {
    return userId;
  }

  public Course getCourse() {
    return course;
  }
}
