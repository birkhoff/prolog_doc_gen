/* This file was generated by SableCC (http://www.sablecc.org/). */

package lexer;

import java.io.*;
import node.*;

@SuppressWarnings("nls")
public class Lexer
{
    protected Token token;
    protected State state = State.PL;

    private PushbackReader in;
    private int line;
    private int pos;
    private boolean cr;
    private boolean eof;
    private final StringBuffer text = new StringBuffer();

    @SuppressWarnings("unused")
    protected void filter() throws LexerException, IOException
    {
        // Do nothing
    }

    public Lexer(@SuppressWarnings("hiding") PushbackReader in)
    {
        this.in = in;
    }
 
    public Token peek() throws LexerException, IOException
    {
        while(this.token == null)
        {
            this.token = getToken();
            filter();
        }

        return this.token;
    }

    public Token next() throws LexerException, IOException
    {
        while(this.token == null)
        {
            this.token = getToken();
            filter();
        }

        Token result = this.token;
        this.token = null;
        return result;
    }

    protected Token getToken() throws IOException, LexerException
    {
        int dfa_state = 0;

        int start_pos = this.pos;
        int start_line = this.line;

        int accept_state = -1;
        int accept_token = -1;
        int accept_length = -1;
        int accept_pos = -1;
        int accept_line = -1;

        @SuppressWarnings("hiding") int[][][] gotoTable = Lexer.gotoTable[this.state.id()];
        @SuppressWarnings("hiding") int[] accept = Lexer.accept[this.state.id()];
        this.text.setLength(0);

        while(true)
        {
            int c = getChar();

            if(c != -1)
            {
                switch(c)
                {
                case 10:
                    if(this.cr)
                    {
                        this.cr = false;
                    }
                    else
                    {
                        this.line++;
                        this.pos = 0;
                    }
                    break;
                case 13:
                    this.line++;
                    this.pos = 0;
                    this.cr = true;
                    break;
                default:
                    this.pos++;
                    this.cr = false;
                    break;
                }

                this.text.append((char) c);

                do
                {
                    int oldState = (dfa_state < -1) ? (-2 -dfa_state) : dfa_state;

                    dfa_state = -1;

                    int[][] tmp1 =  gotoTable[oldState];
                    int low = 0;
                    int high = tmp1.length - 1;

                    while(low <= high)
                    {
                        // int middle = (low + high) / 2;
                        int middle = (low + high) >>> 1;
                        int[] tmp2 = tmp1[middle];

                        if(c < tmp2[0])
                        {
                            high = middle - 1;
                        }
                        else if(c > tmp2[1])
                        {
                            low = middle + 1;
                        }
                        else
                        {
                            dfa_state = tmp2[2];
                            break;
                        }
                    }
                }while(dfa_state < -1);
            }
            else
            {
                dfa_state = -1;
            }

            if(dfa_state >= 0)
            {
                if(accept[dfa_state] != -1)
                {
                    accept_state = dfa_state;
                    accept_token = accept[dfa_state];
                    accept_length = this.text.length();
                    accept_pos = this.pos;
                    accept_line = this.line;
                }
            }
            else
            {
                if(accept_state != -1)
                {
                    switch(accept_token)
                    {
                    case 0:
                        {
                            @SuppressWarnings("hiding") Token token = new0(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 5: state = State.DOC; break;
                            }
                            return token;
                        }
                    case 1:
                        {
                            @SuppressWarnings("hiding") Token token = new1(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 5: state = State.DOC; break;
                            }
                            return token;
                        }
                    case 2:
                        {
                            @SuppressWarnings("hiding") Token token = new2(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 5: state = State.DOC; break;
                            }
                            return token;
                        }
                    case 3:
                        {
                            @SuppressWarnings("hiding") Token token = new3(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 5: state = State.DOC; break;
                            }
                            return token;
                        }
                    case 4:
                        {
                            @SuppressWarnings("hiding") Token token = new4(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 5: state = State.DOC; break;
                            }
                            return token;
                        }
                    case 5:
                        {
                            @SuppressWarnings("hiding") Token token = new5(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 5: state = State.DOC; break;
                            }
                            return token;
                        }
                    case 6:
                        {
                            @SuppressWarnings("hiding") Token token = new6(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 5: state = State.DOC_STAR; break;
                            }
                            return token;
                        }
                    case 7:
                        {
                            @SuppressWarnings("hiding") Token token = new7(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 6: state = State.DOC; break;
                            }
                            return token;
                        }
                    case 8:
                        {
                            @SuppressWarnings("hiding") Token token = new8(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 6: state = State.DOC_STAR; break;
                            }
                            return token;
                        }
                    case 9:
                        {
                            @SuppressWarnings("hiding") Token token = new9(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 6: state = State.PL; break;
                            }
                            return token;
                        }
                    case 10:
                        {
                            @SuppressWarnings("hiding") Token token = new10(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 0: state = State.PL; break;
                            }
                            return token;
                        }
                    case 11:
                        {
                            @SuppressWarnings("hiding") Token token = new11(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 0: state = State.PL_SLASH; break;
                            }
                            return token;
                        }
                    case 12:
                        {
                            @SuppressWarnings("hiding") Token token = new12(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 0: state = State.PL_SINGLE_TICKS; break;
                            }
                            return token;
                        }
                    case 13:
                        {
                            @SuppressWarnings("hiding") Token token = new13(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 0: state = State.PL_DOUBLE_TICKS; break;
                            }
                            return token;
                        }
                    case 14:
                        {
                            @SuppressWarnings("hiding") Token token = new14(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 1: state = State.PL_SLASH; break;
                            }
                            return token;
                        }
                    case 15:
                        {
                            @SuppressWarnings("hiding") Token token = new15(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 1: state = State.PL_STAR; break;
                            }
                            return token;
                        }
                    case 16:
                        {
                            @SuppressWarnings("hiding") Token token = new16(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 1: state = State.PL; break;
                            }
                            return token;
                        }
                    case 17:
                        {
                            @SuppressWarnings("hiding") Token token = new17(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 2: state = State.PL; break;
                            }
                            return token;
                        }
                    case 18:
                        {
                            @SuppressWarnings("hiding") Token token = new18(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 2: state = State.DOC; break;
                            }
                            return token;
                        }
                    case 19:
                        {
                            @SuppressWarnings("hiding") Token token = new19(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 3: state = State.PL_SINGLE_TICKS; break;
                            }
                            return token;
                        }
                    case 20:
                        {
                            @SuppressWarnings("hiding") Token token = new20(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 3: state = State.PL; break;
                            }
                            return token;
                        }
                    case 21:
                        {
                            @SuppressWarnings("hiding") Token token = new21(
                                getText(accept_length),
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 4: state = State.PL_DOUBLE_TICKS; break;
                            }
                            return token;
                        }
                    case 22:
                        {
                            @SuppressWarnings("hiding") Token token = new22(
                                start_line + 1,
                                start_pos + 1);
                            pushBack(accept_length);
                            this.pos = accept_pos;
                            this.line = accept_line;
                            switch(state.id())
                            {
                                case 4: state = State.PL; break;
                            }
                            return token;
                        }
                    }
                }
                else
                {
                    if(this.text.length() > 0)
                    {
                        throw new LexerException(
                            "[" + (start_line + 1) + "," + (start_pos + 1) + "]" +
                            " Unknown token: " + this.text);
                    }

                    @SuppressWarnings("hiding") EOF token = new EOF(
                        start_line + 1,
                        start_pos + 1);
                    return token;
                }
            }
        }
    }

    Token new0(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TDocDescr(text, line, pos); }
    Token new1(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TDocDate(text, line, pos); }
    Token new2(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TDocAuthor(text, line, pos); }
    Token new3(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TDocAtdoc(text, line, pos); }
    Token new4(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TDocIdentifier(text, line, pos); }
    Token new5(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TStringDocString(text, line, pos); }
    Token new6(@SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TStringDocStar(line, pos); }
    Token new7(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TStringStarString(text, line, pos); }
    Token new8(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TStringStarStar(text, line, pos); }
    Token new9(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TDocEnd(text, line, pos); }
    Token new10(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammNoSlash(text, line, pos); }
    Token new11(@SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProrgrammSlash(line, pos); }
    Token new12(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammSingleTicks(text, line, pos); }
    Token new13(@SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammDoubleTicks(line, pos); }
    Token new14(@SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammSlashSlash(line, pos); }
    Token new15(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammToStar(text, line, pos); }
    Token new16(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammSlashAny(text, line, pos); }
    Token new17(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammStarAny(text, line, pos); }
    Token new18(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TDocStart(text, line, pos); }
    Token new19(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammSingleString(text, line, pos); }
    Token new20(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammSingleStringEnd(text, line, pos); }
    Token new21(@SuppressWarnings("hiding") String text, @SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammDoubleString(text, line, pos); }
    Token new22(@SuppressWarnings("hiding") int line, @SuppressWarnings("hiding") int pos) { return new TProgrammDoubleStringEnd(line, pos); }

    private int getChar() throws IOException
    {
        if(this.eof)
        {
            return -1;
        }

        int result = this.in.read();

        if(result == -1)
        {
            this.eof = true;
        }

        return result;
    }

    private void pushBack(int acceptLength) throws IOException
    {
        int length = this.text.length();
        for(int i = length - 1; i >= acceptLength; i--)
        {
            this.eof = false;

            this.in.unread(this.text.charAt(i));
        }
    }

    protected void unread(@SuppressWarnings("hiding") Token token) throws IOException
    {
        @SuppressWarnings("hiding") String text = token.getText();
        int length = text.length();

        for(int i = length - 1; i >= 0; i--)
        {
            this.eof = false;

            this.in.unread(text.charAt(i));
        }

        this.pos = token.getPos() - 1;
        this.line = token.getLine() - 1;
    }

    private String getText(int acceptLength)
    {
        StringBuffer s = new StringBuffer(acceptLength);
        for(int i = 0; i < acceptLength; i++)
        {
            s.append(this.text.charAt(i));
        }

        return s.toString();
    }

    private static int[][][][] gotoTable;
/*  {
        { // PL
            {{0, 33, 1}, {34, 34, 2}, {35, 38, 1}, {39, 39, 3}, {40, 46, 1}, {47, 47, 4}, {48, 237, 1}, },
            {},
            {},
            {},
            {},
        }
        { // PL_SLASH
            {{0, 33, 1}, {35, 38, 1}, {40, 41, 1}, {42, 42, 2}, {43, 46, 1}, {47, 47, 3}, {48, 237, 1}, },
            {},
            {},
            {},
        }
        { // PL_STAR
            {{0, 33, 1}, {35, 38, 1}, {40, 41, 1}, {42, 42, 2}, {43, 46, 1}, {48, 237, 1}, },
            {},
            {{9, 9, 3}, {10, 10, 4}, {13, 13, 5}, {32, 32, 6}, {42, 42, 7}, },
            {{9, 42, -4}, },
            {{9, 42, -4}, },
            {{9, 42, -4}, },
            {{9, 42, -4}, },
            {{9, 42, -4}, },
        }
        { // PL_SINGLE_TICKS
            {{0, 33, 1}, {35, 38, 1}, {39, 39, 2}, {40, 63, 1}, {65, 237, 1}, },
            {{0, 33, 1}, {35, 63, 1}, {65, 237, 1}, },
            {{0, 237, -3}, },
        }
        { // PL_DOUBLE_TICKS
            {{0, 33, 1}, {34, 34, 2}, {35, 36, 1}, {38, 63, 1}, {65, 237, 1}, },
            {{0, 36, 1}, {38, 237, -2}, },
            {{0, 237, -3}, },
        }
        { // DOC
            {{0, 8, 1}, {9, 9, 2}, {10, 10, 3}, {11, 12, 1}, {13, 13, 4}, {14, 31, 1}, {32, 32, 5}, {33, 41, 1}, {42, 42, 6}, {43, 63, 1}, {64, 64, 7}, {65, 90, 8}, {91, 94, 1}, {95, 95, 9}, {96, 96, 1}, {97, 122, 10}, {123, 237, 1}, },
            {{0, 41, 1}, {43, 63, 1}, {65, 237, 1}, },
            {{0, 41, -2}, {43, 64, -2}, {65, 237, 1}, },
            {{0, 237, -4}, },
            {{0, 237, -4}, },
            {{0, 237, -4}, },
            {},
            {{65, 65, 11}, {66, 67, 12}, {68, 68, 13}, {69, 90, 12}, {95, 95, 14}, {97, 97, 15}, {98, 99, 16}, {100, 100, 17}, {101, 122, 16}, },
            {{0, 63, -3}, {65, 237, -2}, },
            {{0, 237, -10}, },
            {{0, 237, -10}, },
            {{9, 9, 18}, {10, 10, 19}, {13, 13, 20}, {32, 32, 21}, {65, 84, 12}, {85, 85, 22}, {86, 90, 12}, {95, 95, 14}, {97, 116, 16}, {117, 117, 23}, {118, 122, 16}, },
            {{9, 32, -13}, {65, 90, 12}, {95, 95, 14}, {97, 122, 16}, },
            {{9, 32, -13}, {65, 65, 24}, {66, 68, 12}, {69, 69, 25}, {70, 90, 12}, {95, 95, 14}, {97, 97, 26}, {98, 100, 16}, {101, 101, 27}, {102, 122, 16}, },
            {{9, 122, -14}, },
            {{9, 122, -13}, },
            {{9, 122, -14}, },
            {{9, 122, -15}, },
            {{9, 32, -13}, },
            {{9, 32, -13}, },
            {{9, 32, -13}, },
            {{9, 32, -13}, },
            {{9, 32, -13}, {65, 83, 12}, {84, 84, 28}, {85, 90, 12}, {95, 95, 14}, {97, 115, 16}, {116, 116, 29}, {117, 122, 16}, },
            {{9, 122, -24}, },
            {{9, 83, -24}, {84, 84, 30}, {85, 115, -24}, {116, 116, 31}, {117, 122, 16}, },
            {{9, 32, -13}, {65, 82, 12}, {83, 83, 32}, {84, 90, 12}, {95, 95, 14}, {97, 114, 16}, {115, 115, 33}, {116, 122, 16}, },
            {{9, 122, -26}, },
            {{9, 122, -27}, },
            {{9, 32, -13}, {65, 71, 12}, {72, 72, 34}, {73, 90, 12}, {95, 95, 14}, {97, 103, 16}, {104, 104, 35}, {105, 122, 16}, },
            {{9, 122, -30}, },
            {{9, 32, -13}, {65, 68, 12}, {69, 69, 36}, {70, 95, -15}, {97, 100, 16}, {101, 101, 37}, {102, 122, 16}, },
            {{9, 122, -32}, },
            {{9, 32, -13}, {65, 66, 12}, {67, 67, 38}, {68, 90, 12}, {95, 95, 14}, {97, 98, 16}, {99, 99, 39}, {100, 122, 16}, },
            {{9, 122, -34}, },
            {{9, 32, -13}, {65, 78, 12}, {79, 79, 40}, {80, 90, 12}, {95, 95, 14}, {97, 110, 16}, {111, 111, 41}, {112, 122, 16}, },
            {{9, 122, -36}, },
            {{9, 9, 42}, {10, 10, 43}, {13, 13, 44}, {32, 32, 45}, {65, 122, -14}, },
            {{9, 122, -38}, },
            {{9, 32, -13}, {65, 81, 12}, {82, 82, 46}, {83, 90, 12}, {95, 95, 14}, {97, 113, 16}, {114, 114, 47}, {115, 122, 16}, },
            {{9, 122, -40}, },
            {{9, 81, -40}, {82, 82, 48}, {83, 113, -40}, {114, 114, 49}, {115, 122, 16}, },
            {{9, 122, -42}, },
            {{9, 32, -38}, },
            {{9, 32, -38}, },
            {{9, 32, -38}, },
            {{9, 32, -38}, },
            {{9, 9, 50}, {10, 10, 51}, {13, 13, 52}, {32, 32, 53}, {65, 72, 12}, {73, 73, 54}, {74, 90, 12}, {95, 95, 14}, {97, 104, 16}, {105, 105, 55}, {106, 122, 16}, },
            {{9, 122, -48}, },
            {{9, 9, 56}, {10, 10, 57}, {13, 13, 58}, {32, 32, 59}, {65, 122, -14}, },
            {{9, 122, -50}, },
            {{9, 32, -48}, },
            {{9, 32, -48}, },
            {{9, 32, -48}, },
            {{9, 32, -48}, },
            {{9, 32, -13}, {65, 79, 12}, {80, 80, 60}, {81, 90, 12}, {95, 95, 14}, {97, 111, 16}, {112, 112, 61}, {113, 122, 16}, },
            {{9, 122, -56}, },
            {{9, 32, -50}, },
            {{9, 32, -50}, },
            {{9, 32, -50}, },
            {{9, 32, -50}, },
            {{9, 83, -24}, {84, 84, 62}, {85, 115, -24}, {116, 116, 63}, {117, 122, 16}, },
            {{9, 122, -62}, },
            {{9, 32, -13}, {65, 72, 12}, {73, 73, 64}, {74, 104, -48}, {105, 105, 65}, {106, 122, 16}, },
            {{9, 122, -64}, },
            {{9, 78, -36}, {79, 79, 66}, {80, 110, -36}, {111, 111, 67}, {112, 122, 16}, },
            {{9, 122, -66}, },
            {{9, 32, -13}, {65, 77, 12}, {78, 78, 68}, {79, 90, 12}, {95, 95, 14}, {97, 109, 16}, {110, 110, 69}, {111, 122, 16}, },
            {{9, 122, -68}, },
            {{9, 32, -48}, {65, 122, -14}, },
            {{9, 122, -70}, },
        }
        { // DOC_STAR
            {{0, 41, 1}, {42, 42, 2}, {43, 46, 1}, {47, 47, 3}, {48, 63, 1}, {65, 237, 1}, },
            {},
            {{42, 42, 2}, },
            {{9, 9, 4}, {10, 10, 5}, {13, 13, 6}, {32, 32, 7}, },
            {{9, 32, -5}, },
            {{9, 32, -5}, },
            {{9, 32, -5}, },
            {{9, 32, -5}, },
        }
    };*/

    private static int[][] accept;
/*  {
        // PL
        {-1, 10, 13, 12, 11, },
        // PL_SLASH
        {-1, 16, 15, 14, },
        // PL_STAR
        {-1, 17, 18, 18, 18, 18, 18, 18, },
        // PL_SINGLE_TICKS
        {19, 19, 19, },
        // PL_DOUBLE_TICKS
        {21, 21, 21, },
        // DOC
        {5, 5, 5, 5, 5, 5, 6, -1, 4, 4, 4, -1, -1, -1, -1, -1, -1, -1, 3, 3, 3, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 2, 2, 2, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, },
        // DOC_STAR
        {8, 7, 8, 9, 9, 9, 9, 9, },

    };*/

    public static class State
    {
        public final static State PL = new State(0);
        public final static State PL_SLASH = new State(1);
        public final static State PL_STAR = new State(2);
        public final static State PL_SINGLE_TICKS = new State(3);
        public final static State PL_DOUBLE_TICKS = new State(4);
        public final static State DOC = new State(5);
        public final static State DOC_STAR = new State(6);

        private int id;

        private State(@SuppressWarnings("hiding") int id)
        {
            this.id = id;
        }

        public int id()
        {
            return this.id;
        }
    }

    static 
    {
        try
        {
            DataInputStream s = new DataInputStream(
                new BufferedInputStream(
                Lexer.class.getResourceAsStream("lexer.dat")));

            // read gotoTable
            int length = s.readInt();
            gotoTable = new int[length][][][];
            for(int i = 0; i < gotoTable.length; i++)
            {
                length = s.readInt();
                gotoTable[i] = new int[length][][];
                for(int j = 0; j < gotoTable[i].length; j++)
                {
                    length = s.readInt();
                    gotoTable[i][j] = new int[length][3];
                    for(int k = 0; k < gotoTable[i][j].length; k++)
                    {
                        for(int l = 0; l < 3; l++)
                        {
                            gotoTable[i][j][k][l] = s.readInt();
                        }
                    }
                }
            }

            // read accept
            length = s.readInt();
            accept = new int[length][];
            for(int i = 0; i < accept.length; i++)
            {
                length = s.readInt();
                accept[i] = new int[length];
                for(int j = 0; j < accept[i].length; j++)
                {
                    accept[i][j] = s.readInt();
                }
            }

            s.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException("The file \"lexer.dat\" is either missing or corrupted.");
        }
    }
}
