package hu.bubi.chef.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ReceptToOsszetevo entity.
 */
public class ReceptToOsszetevoDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String mennyiseg;

    private String megjegyzes;

    private Long receptId;

    private Long osszetevoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(String mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }

    public Long getReceptId() {
        return receptId;
    }

    public void setReceptId(Long receptId) {
        this.receptId = receptId;
    }

    public Long getOsszetevoId() {
        return osszetevoId;
    }

    public void setOsszetevoId(Long osszetevoId) {
        this.osszetevoId = osszetevoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReceptToOsszetevoDTO receptToOsszetevoDTO = (ReceptToOsszetevoDTO) o;
        if (receptToOsszetevoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), receptToOsszetevoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReceptToOsszetevoDTO{" +
            "id=" + getId() +
            ", mennyiseg='" + getMennyiseg() + "'" +
            ", megjegyzes='" + getMegjegyzes() + "'" +
            ", recept=" + getReceptId() +
            ", osszetevo=" + getOsszetevoId() +
            "}";
    }
}
