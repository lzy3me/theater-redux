package com.lzy3me.theater.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String m_name;
    private String m_desc;
    private Short m_length;
    private String m_tag;
    private String m_starring;
    private LocalDate m_release;

    @CreatedDate
    private LocalDate createdAt;

    public Movies() {
    }

    public Movies(String m_name, String m_desc, Short m_length, String m_tag, String m_starring, LocalDate m_release) {
        this.m_name = m_name;
        this.m_desc = m_desc;
        this.m_length = m_length;
        this.m_tag = m_tag;
        this.m_starring = m_starring;
        this.m_release = m_release;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movies movies = (Movies) o;
        return Objects.equals(id, movies.id) && Objects.equals(m_name, movies.m_name) && Objects.equals(m_desc, movies.m_desc) && Objects.equals(m_length, movies.m_length) && Objects.equals(m_tag, movies.m_tag) && Objects.equals(m_starring, movies.m_starring) && Objects.equals(m_release, movies.m_release) && Objects.equals(createdAt, movies.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, m_name, m_desc, m_length, m_tag, m_starring, m_release, createdAt);
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id=" + id +
                ", m_name='" + m_name + '\'' +
                ", m_desc='" + m_desc + '\'' +
                ", m_length=" + m_length +
                ", m_tag='" + m_tag + '\'' +
                ", m_starring='" + m_starring + '\'' +
                ", m_release=" + m_release +
                ", createdAt=" + createdAt +
                '}';
    }
}
