package hu.bubi.chef.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the HashTag entity.
 */
public class HashTagDTO implements Serializable {

    private Long id;

    private String nev;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HashTagDTO hashTagDTO = (HashTagDTO) o;
        if (hashTagDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hashTagDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HashTagDTO{" +
            "id=" + getId() +
            ", nev='" + getNev() + "'" +
            "}";
    }
}
