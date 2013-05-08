package src.main.java;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;

import analysis.*;
import lexer.*;
import node.*;
import parser.*;


public class DocLexer extends Lexer {
	
	private static final String PRAGMA_END = "@*/";
	private static final String PRAGMA_START = "/**@";
	private TString string = null;
	private StringBuilder commentBuffer = null;
	private List<Token> pragmas = new ArrayList<Token>();


	// private final List<Token> tokenList;

	private boolean debugOutput = false;

	public DocLexer(final PushbackReader in, 
			final int tokenCountPrediction) {
		super(in);

		// if (tokenCountPrediction > 10) {
		// tokenList = new ArrayList<Token>(tokenCountPrediction);
		// } else {
		// tokenList = new ArrayList<Token>();
		// }
	}



	public DocLexer(final PushbackReader in) {
		this(in, 0);
	}

	@Override
	protected void filter() throws LexerException, IOException {

		if (state.equals(State.DOC)) {
			collectDoc();
		}

		if (token instanceof TString) {
			// google for howto-unescape-a-java-string-literal-in-java
			// quickfix: we do nothing just strip off the "
			final String literal = token.getText();
			token.setText(literal.substring(1, literal.length() - 1));
		}
		if (token != null) {
			
			buildTokenList();

			if (debugOutput && !(token instanceof TIgnore)
					&& !(token instanceof EOF)) {
				System.out.print(token.getClass().getSimpleName() + "('"
						+ token.getText() + "') ");
			}
		}
	}

	private void buildTokenList() {
		if (token != null) {
			// tokenList.add(token);
		}
	}

	private void collectDoc() throws LexerException, IOException {
		if (token instanceof EOF) {
			// make sure we don't loose this token, needed for error message
			// tokenList.add(token);
			final int line = token.getLine() - 1;
			final int pos = token.getPos() - 1;
			final String text = token.getText();

			throw new LexerException("Doc not closed");
		}

		// starting a new comment
		if (string == null) {
			string = (TString) token;
			commentBuffer = new StringBuilder(token.getText());
			token = null;
		} else {
			commentBuffer.append(token.getText());

			// end of comment reached?
			if (token instanceof TCommentEnd) {
				String text = commentBuffer.toString();
				string.setText(text);
				token = string;
				string = null;
				commentBuffer = null;
				state = State.PL;

				if (text.startsWith(PRAGMA_START)) {
					String pragmaText = "";
					if (text.endsWith(PRAGMA_END))
						pragmaText = text.substring(3, text.length() - 3)
								.trim();
					else
						pragmaText = text.substring(3, text.length() - 2)
								.trim();
					pragmas.add( pragmaText );
				}
			} else {
				token = null;
			}
		}
	}

	public List<Token> getPragmas() {
		return pragmas;
	}

	public void setDebugOutput(final boolean debugOutput) {
		this.debugOutput = debugOutput;
	}
	
	
}