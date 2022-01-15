public class UnionFind {
    void makeset(Cell in)
    {
        LLAddOnly set = new LLAddOnly();
        set.add(in);
    }
    LLAddOnly find(Cell x)
    {
        return x.head;
    }
    void union(Cell a, Cell b)
    {
        if (a.head == b.head) return;
        if (a.head == null || b.head == null) return;

        a.head.last.next = b.head.first; // so end of first list points to beginning of second list

        a.head.last = b.head.last; // correct last of a to point to last of b

        Cell current = b.head.first;
        while (current != null)
        {
            current.head = a.head; // adjust header to point to header of first list
            current = current.next; // advance to next cell
        }
    }
}
