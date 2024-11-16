package com.itgenz.studentmanage.models;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;

public class Province {
    private String name;
    private int code;
    private String division_type;
    private String codename;
    private int phone_code;
    private List<District> districts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDivision_type() {
        return division_type;
    }

    public void setDivision_type(String division_type) {
        this.division_type = division_type;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public int getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(int phone_code) {
        this.phone_code = phone_code;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    @Override
    public String toString() {
        return name;
    }

    public Province(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Province p = (Province) obj;
        if (obj == null) {
            return false;
        }
        return p.getCode() == this.code;
    }

    public static class District {
        private String name;
        private int code;
        private String division_type;
        private String codename;
        private int province_code;
        private List<Ward> wards;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDivision_type() {
            return division_type;
        }

        public void setDivision_type(String division_type) {
            this.division_type = division_type;
        }

        public String getCodename() {
            return codename;
        }

        public void setCodename(String codename) {
            this.codename = codename;
        }

        public int getProvince_code() {
            return province_code;
        }

        public void setProvince_code(int province_code) {
            this.province_code = province_code;
        }

        public List<Ward> getWards() {
            return wards;
        }

        public void setWards(List<Ward> wards) {
            this.wards = wards;
        }

        @Override
        public String toString() {
            return name;
        }

        public District(int code) {
            this.code = code;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            District d = (District) obj;
            if (obj == null) {
                return false;
            }
            return d.getCode() == this.code;
        }

        public static class Ward {
            private String name;
            private int code;
            private String codename;
            private String division_type;
            private String short_codename;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getCodename() {
                return codename;
            }

            public void setCodename(String codename) {
                this.codename = codename;
            }

            public String getDivision_type() {
                return division_type;
            }

            public void setDivision_type(String division_type) {
                this.division_type = division_type;
            }

            public String getShort_codename() {
                return short_codename;
            }

            public void setShort_codename(String short_codename) {
                this.short_codename = short_codename;
            }

            @Override
            public String toString() {
                return name;
            }

            public Ward(int code) {
                this.code = code;
            }

            @Override
            public boolean equals(@Nullable Object obj) {
                Ward w = (Ward) obj;
                if (obj == null) {
                    return false;
                }
                return w.getCode() == this.code;
            }
        }
    }
}
