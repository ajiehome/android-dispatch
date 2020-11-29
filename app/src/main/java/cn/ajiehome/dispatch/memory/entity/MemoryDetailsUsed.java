package cn.ajiehome.dispatch.memory.entity;

/**
 * @author Jie
 */
public class MemoryDetailsUsed {
    private Integer pid;
    private Integer start;
    private Integer end;

    public MemoryDetailsUsed(Integer pid, Integer start, Integer end) {
        this.pid = pid;
        this.start = start;
        this.end = end;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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
