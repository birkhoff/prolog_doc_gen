/* This file was generated by SableCC (http://www.sablecc.org/). */

package parser;

import node.*;
import analysis.*;

class TokenIndex extends AnalysisAdapter
{
    int index;

    @Override
    public void caseTDocDescr(@SuppressWarnings("unused") TDocDescr node)
    {
        this.index = 0;
    }

    @Override
    public void caseTDocDate(@SuppressWarnings("unused") TDocDate node)
    {
        this.index = 1;
    }

    @Override
    public void caseTDocAuthor(@SuppressWarnings("unused") TDocAuthor node)
    {
        this.index = 2;
    }

    @Override
    public void caseTDocAtdoc(@SuppressWarnings("unused") TDocAtdoc node)
    {
        this.index = 3;
    }

    @Override
    public void caseTDocIdentifier(@SuppressWarnings("unused") TDocIdentifier node)
    {
        this.index = 4;
    }

    @Override
    public void caseTStringDocString(@SuppressWarnings("unused") TStringDocString node)
    {
        this.index = 5;
    }

    @Override
    public void caseTStringDocStar(@SuppressWarnings("unused") TStringDocStar node)
    {
        this.index = 6;
    }

    @Override
    public void caseTStringStarString(@SuppressWarnings("unused") TStringStarString node)
    {
        this.index = 7;
    }

    @Override
    public void caseTDocEnd(@SuppressWarnings("unused") TDocEnd node)
    {
        this.index = 8;
    }

    @Override
    public void caseTDocStart(@SuppressWarnings("unused") TDocStart node)
    {
        this.index = 9;
    }

    @Override
    public void caseEOF(@SuppressWarnings("unused") EOF node)
    {
        this.index = 10;
    }
}
