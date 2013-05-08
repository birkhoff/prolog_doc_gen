/* This file was generated by SableCC (http://www.sablecc.org/). */

package node;

import analysis.*;

@SuppressWarnings("nls")
public final class TProgrammSingleTicks extends Token
{
    public TProgrammSingleTicks(String text)
    {
        setText(text);
    }

    public TProgrammSingleTicks(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TProgrammSingleTicks(getText(), getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTProgrammSingleTicks(this);
    }
}
