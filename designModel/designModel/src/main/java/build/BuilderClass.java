package build;

/**
 * @Author: 赵鑫
 * @Date: 2022/8/18
 */
public class BuilderClass {


    private String name;
    private String sex;
    private Integer age;

    public static BuilderClass.Builder builder() {
        return new BuilderClass.Builder();
    }

    public final static class Builder {
        private String name;
        private String sex;
        private Integer age;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder sex(String sex) {
            this.sex = sex;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public BuilderClass build() {
            return new BuilderClass(this);
        }
    }

    private BuilderClass(Builder builder) {
        this.name = builder.name;
        this.sex = builder.sex;
        this.age = builder.age;
    }

    @Override
    public String toString() {
        return "BuilderClass{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}

class Client {
    public static void main(String[] args) {
        BuilderClass builderClass = new BuilderClass.Builder()
                .name("name")
                .sex("man")
                .age(23)
                .build();
        System.out.println(builderClass);
        BuilderClass builderClass2 = BuilderClass.builder().build();
    }
}
