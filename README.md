# chessclone  
~~#Today is the last open Lab.~~
# TODO:  

#WE NEED A BLACK QUEEN IMAGe (It seems as if the upload stopped short. I'll get the file(s) up as fast as I can)

 Get game processes and Swing up and running. We'll need to also need to make the documentation of the classes and methods without documentation. ~~I'll be creating icons to use for the pieces this week, and if I need to, a board.~~ Images are uploaded.
 
 Any missing information in the Chess and ChessGame classes needs to be filled in. ~~I(Kika) have a little more work to do on the added Piece abstract method getMoveSpan() for Knight, rook, Queen, and King.~~


 Could we please code in an IDE instead of on GitHub from now on? I (David) spent like 2 hours just fixing a bunch of syntax errors. I witnessed so many gruesome errors that I think I got PTSD :P.
 BTW, while fixing these errors I might have changed the intended behavior so check and fix. 
 
 And, could we also make method names more clear? For example, the method name impededCheck() returns a boolean but its  ambiguous to what it actually returns. (it returns whether the piece's path to the location is impeded) And the method notSameColor() gives a negative boolean which is hard to understand (It checks if the piece is of the same color or not). That's why i change the names
 
 #UPDATE: I saw the stupidest possible bugs in the movement scheme. ALL movement schemes are fixed. 
 
 However, the check and castle methods for check are unable to be fixed until the GUI is fixed. I tested it and the coords do not line up. Until this is fixed, I will not be able to accurately fix my methods. The problem is that the inputted letter rank on the display needs to be reversed (i.e: a1 == h1, b1 == g1, c1 == f1, etc.). I have absolutely no idea how to modify your code to make this work, so you will have to do this. 
 
 And Danny, could you do stuff?

TEST
