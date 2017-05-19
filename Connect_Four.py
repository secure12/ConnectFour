'''
	CSCI3180 Principles of Programming Languages

	--- Declaration ---

	I declare that the assignment here submitted is original except for source
	material explicitly acknowledged. I also acknowledge that I am aware of
	University policy and regulations on honesty in academic work, and of the
	disciplinary guidelines and procedures applicable to breaches of such policy
	and regulations, as contained in the website
	http://www.cuhk.edu.hk/policy/academichonesty/

	Assignment 2 
	Name : Ho Chin Hei
	Student ID : 1155063921
	Email Addr : trbhei@gmail.com
'''

import random


# function for spanning the board
def f(i, j, l):
	return tuple(map(lambda x:x+i+6*j, l))

# True if a is subset of b
def is_subset(a, b):
	for x in a:
		if x not in b:
			return False
	return True

# Generating all sets of 4 consecutive rows/diagonals/columns
winning_moves = set()
for i in xrange(3):
	for j in xrange(7):
		winning_moves.add(f(i,j,(0,1,2,3)))
for i in xrange(6):
	for j in xrange(4):
		winning_moves.add(f(i,j,(0,6,12,18)))
for i in xrange(3):
	for j in xrange(4):
		winning_moves.add(f(i,j,(0,7,14,21)))
		winning_moves.add(f(i,j,(3,8,13,18)))


class Connect_Four(object):

	# resetting all attributes
	def __init__(self):
		self.game_board = [[] for _ in xrange(7)]
		self.end = False
		self.win_discs = []

	def print_game_board(self):
		print "| 1 | 2 | 3 | 4 | 5 | 6 | 7 |"
		print "-----------------------------"
		# self.end True if this game ends
		if (self.end == False):
			for i in xrange(5, -1, -1):
				for j in xrange(7):
					print '|',
					if (len(self.game_board[j]) > i):
						print "%s" % self.game_board[j][i],
					else:
						print " ",
				print "|"
				print "-----------------------------"
		else:
			s = "\033[0m|"
			for i in xrange(5, -1, -1):
				for j in xrange(7):
					if (len(self.game_board[j]) > i):
						if i+6*j in self.win_discs:
							print "%s\033[7m %s" % (s,self.game_board[j][i]),
						else:
							print '%s %s' % (s,self.game_board[j][i]),
					else:
						print "%s  " % s,
				print "%s" % s
				print "-----------------------------"

	def start_game(self):
		self.__init__()
		# repeat until a correct player 1 is chosen
		while(True):
			self.player1 = self.choose_player(1)
			if (self.player1):
				break
			print "Choose either 1, 2 or 3!!"
		# repeat until a correct player 2 is chosen
		while(True):
			self.player2 = self.choose_player(2)
			if (self.player2):
				break
			print "Choose either 1, 2 or 3!!"
		# player 1 first to move
		self.turn = self.player1
		# counter for number of discs
		step = 0
		while(step < 42):
 			self.print_game_board()
			move = self.move()
			'''
			move
				"alphabet": user entered some alphabet / ^C etc.
				"oob": user entered some integers < 1 or > 7
				"full": the column user entered is full already
			'''
			if (move == "alphabet" or move == "oob"):
				print
				print "Choose from 1 to 7!!!"
				continue
			elif (move == "full"):
				print
				print "The position is full!!!"
				continue
			self.check_win(move)
			step += 1
			if (self.win_discs == []):
				self.flip_turn()
			else:
				break
		if (step != 42):
			self.end = True
			print "Player %s has won!!!" % self.turn.player_symbol
			self.print_game_board()
			return self.turn.player_symbol
		else:
			print "\t    Draw!!!"
			self.print_game_board()
			return None

	def choose_player(self, n):
		n -= 1
		s = [["first", "second"], ['O', 'X']]
 		print
		print "Please choose the %s player:" % s[0][n]
 		print "1.Computer"
 		print "2.Human"
 		print "3.AI"
		print "4.Smart AI"
 		choice = raw_input("Your choice is:")
		try:
			choice = int(choice)- 1
		except:
			return None
		else:
			if (choice == 0):
 				print "Player %s is Computer" % s[1][n]
				return Computer(s[1][n])
			elif (choice == 1):
 				print "Player %s is Human" % s[1][n]
				return Human(s[1][n])
			elif (choice == 2):
 				print "Player %s is AI" % s[1][n]
				return SmartAI(s[1][n])
			elif (choice == 3):
 				print "Player %s is Smart AI" % s[1][n]
				return AI(s[1][n])
			else:
				return None

	# update board
	def move(self):
		pl = self.turn
		gb = self.game_board
 		print "Player %s\'s turn." % self.turn.player_symbol
		move = pl.next_column(self.game_board)
		if (type(move) == str):
			return move
		temp = len(gb[move])+6*move
		gb[move].append(pl.player_symbol)
 		print
 		print "Player %s has made the move[%d]!" % (pl.player_symbol, move+1)
		self.turn.discs.add(temp)
		return temp
	
	# self explanatory
	def flip_turn(self):
		if (self.turn == self.player1):
			self.turn = self.player2
		else:
			self.turn = self.player1
	
	# check if the current player win this round
	def check_win(self, indx):
		for combo in winning_moves:
			if (indx in combo and is_subset(combo,self.turn.discs)):
				self.win_discs.extend(combo)

class Player(object):
	def __init__(self, PlayerSymbol):
		self.player_symbol = PlayerSymbol
		self.discs = set()

class Human(Player):
	def next_column(self, game_board):
		move = raw_input("Make a move[1-7]:")
		try:
			move = int(move) - 1
		except:
			return "alphabet"
		else:
			if (move < 0 or move > 6):
				return "oob"
			elif(len(game_board[move]) == 6):
				return "full"
			return move

class Computer(Player):
	def next_column(self, game_board):
		possible_moves = [x for x in xrange(7) if len(game_board[x]) != 6]
		move = random.choice(possible_moves)
		return move

class AI(Player):
	# difficulty*2 is levels of depth to search in search tree
	def __init__(self, PlayerSymbol, difficulty=2):
		Player.__init__(self, PlayerSymbol)
		if (PlayerSymbol == 'O'):
			self.oppoSymbol = 'X'
		else:
			self.oppoSymbol = 'O'
		self.oppo_discs = set()
		self.depth = difficulty * 2

	# get indices of opponent's discs
	def update_oppo_discs(self, game_board):
		for i in xrange(7):
			try:
				if (game_board[i][-1] == self.oppoSymbol):
					self.oppo_discs.add(len(game_board[i])-1 + 6*i)
			except IndexError:
				continue

	def next_column(self, game_board):
		self.update_oppo_discs(game_board)
		valid_moves = [i for i in xrange(7) if len(game_board[i]) != 6]
		if (len(valid_moves) == 1):
			return valid_moves[0]
		# check if this move can win
		for move in valid_moves:
			indx = len(game_board[move])+move*6
			for combo in winning_moves:
				if is_subset(combo,self.discs.union([indx])):
					return move
		# check if opponent wins next round
		for move in valid_moves:
			indx = len(game_board[move])+6*move
			for combo in winning_moves:
				if is_subset(combo,self.oppo_discs.union([indx])):
					return move
		# Alpha-Beta Pruning
		v = -2
		for move in random.sample(valid_moves, len(valid_moves)):
			indx = len(game_board[move]) + 6*move
			self.discs.add(indx)
			game_board[move].append(self.player_symbol)
			if (len(game_board[move]) == 6):
				valid_moves.remove(move)
			temp = self.AlphaBeta(game_board, valid_moves, self.depth-1, -1, 70, False)
			game_board[move].pop()
			self.discs.remove(indx)
			if (valid_moves.count(move) == 0):
				valid_moves.append(move)
			if (v < temp):
				v = temp
				best_move = move
		return best_move
	# recursive a-b function	
	def AlphaBeta(self, game_board, valid_moves, depth, alpha, beta, myTurn):
		if (depth == 0):
			return self.Heuristic(game_board)
		if (myTurn):
			v = -1
			for move in random.sample(valid_moves, len(valid_moves)):
				indx = len(game_board[move]) + 6*move
				self.discs.add(indx)
				game_board[move].append(self.player_symbol)
				if (len(game_board[move]) == 6):
					valid_moves.remove(move)
				v = max(v, self.AlphaBeta(game_board, valid_moves, depth-1, alpha, beta, False))
				if (valid_moves.count(move) == 0):
					valid_moves.append(move)
				game_board[move].pop()
				self.discs.remove(indx)
				alpha = max(alpha, v)
				if (beta <= alpha):
					break
			return v
		else:
			v = 70
			for move in random.sample(valid_moves, len(valid_moves)):
				indx = len(game_board[move]) + 6*move
				self.oppo_discs.add(indx)
				game_board[move].append(self.oppoSymbol)
				if (len(game_board[move]) == 6):
					valid_moves.remove(move)
				v = min(v, self.AlphaBeta(game_board, valid_moves, depth-1, alpha, beta, True))
				if (valid_moves.count(move) == 0):
					valid_moves.append(move)
				game_board[move].pop()
				self.oppo_discs.remove(indx)
				beta = min(beta, v)
				if (beta <= alpha):
					break
			return v
	# heuristic function for a-b pruning
	def Heuristic(self, game_board):
		h = 0
		for combo in winning_moves:
			L = set(combo) - self.oppo_discs
			if (len(L) == 0):
				h = -10
			L = set(combo) - self.discs
			if (len(L) == 0):
				h += 20
			elif (len(L) == 1):
				L = L.pop()
				if (len(game_board[L/6]) <= L%6):
					h += 1
		return h

#Smart AI is less smart LOL
class SmartAI(AI):
	def next_column(self, game_board):
		self.update_oppo_discs(game_board)
		valid_moves = [i for i in xrange(7) if len(game_board[i]) != 6]
		if (len(valid_moves) == 1):
			return valid_moves[0]
		# check if I can win this round
		for move in valid_moves:
			indx = len(game_board[move])+move*6
			for combo in winning_moves:
				if is_subset(combo,self.discs.union([indx])):
					return move
		# check if opponent wins next round - block
		for move in valid_moves:
			indx = len(game_board[move])+6*move
			for combo in winning_moves:
				if is_subset(combo,self.oppo_discs.union([indx])):
					return move
		# otherwise perform random move
		return random.choice(valid_moves)

def update(ai, game_board):
	ai.discs = set()
	ai.oppo_discs = set()
	for i in xrange(7):
		for j in range(len(game_board[i])):
			if game_board[i][j] == ai.player_symbol:
				ai.discs.add(6*i+j)
			else:
				ai.oppo_discs.add(6*i+j)
'''
game_board = ["","XOO","OO","OOXXOX","XXOOOX","XX","OXOXOX"]
for i in range(len(game_board)):
	game_board[i] = list(game_board[i])
ai = AI('X')
#game_board[1].append('X')
update(ai, game_board)
for move in xrange(7):
	if (len(game_board[move]) == 6):
		continue
	temp = 6*move + len(game_board[move])
	game_board[move].append(ai.oppoSymbol)
	ai.oppo_discs.add(temp)
	print move, ai.next_column(game_board)
	ai.oppo_discs.remove(temp)
	game_board[move].pop()
print ai.next_column(game_board)
'''

# main statements
ng = Connect_Four()
ng.start_game()
