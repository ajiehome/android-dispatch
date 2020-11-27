package cn.ajiehome.dispatch.memory.entity;

/**
 * @author Jie
 */
public class FragmentMemory {
    private Integer start;//包括
    private Integer end;//不包括
    public FragmentMemory(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
