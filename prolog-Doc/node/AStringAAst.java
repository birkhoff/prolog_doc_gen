/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class AStringAAst extends PAst
{
    private TStringDocString _stringDocString_;

    public AStringAAst()
    {
        // Constructor
    }

    public AStringAAst(
        @SuppressWarnings("hiding") TStringDocString _stringDocString_)
    {
        // Constructor
        setStringDocString(_stringDocString_);

    }

    @Override
    public Object clone()
    {
        return new AStringAAst(
            cloneNode(this._stringDocString_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAStringAAst(this);
    }

    public TStringDocString getStringDocString()
    {
        return this._stringDocString_;
    }

    public void setStringDocString(TStringDocString node)
    {
        if(this._stringDocString_ != null)
        {
            this._stringDocString_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._stringDocString_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._stringDocString_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._stringDocString_ == child)
        {
            this._stringDocString_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._stringDocString_ == oldChild)
        {
            setStringDocString((TStringDocString) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
