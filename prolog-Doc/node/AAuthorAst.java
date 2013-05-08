/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import java.util.*;
import analysis.*;

@SuppressWarnings("nls")
public final class AAuthorAst extends PAst
{
    private final LinkedList<PAst> _ast_ = new LinkedList<PAst>();

    public AAuthorAst()
    {
        // Constructor
    }

    public AAuthorAst(
        @SuppressWarnings("hiding") List<?> _ast_)
    {
        // Constructor
        setAst(_ast_);

    }

    @Override
    public Object clone()
    {
        return new AAuthorAst(
            cloneList(this._ast_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAuthorAst(this);
    }

    public LinkedList<PAst> getAst()
    {
        return this._ast_;
    }

    public void setAst(List<?> list)
    {
        for(PAst e : this._ast_)
        {
            e.parent(null);
        }
        this._ast_.clear();

        for(Object obj_e : list)
        {
            PAst e = (PAst) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._ast_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._ast_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._ast_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        for(ListIterator<PAst> i = this._ast_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PAst) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
