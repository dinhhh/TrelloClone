package com.example.demo.Board;

import com.example.demo.entity.Board;
import com.example.demo.entity.Card;
import com.example.demo.entity.RecentlyViewedBoard;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.RecentlyViewedBoardRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BoardController {
    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private RecentlyViewedBoardRepository recentRepo;

    @DeleteMapping("/api/board/{boardID}")
    public ResponseEntity<String> deleteBoard(@PathVariable String boardID) {
        Optional<Board> boards = boardRepo.findById(Long.valueOf(boardID));
        if (!boards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Board board = boards.get();
        Set<Card> cards = board.getCards();
        for (Card c : cards) {
            cardRepo.delete(c);
        }
        List<RecentlyViewedBoard> recents = recentRepo.findWhereBoardIDEqual(board.getId());
        for (RecentlyViewedBoard r : recents) {
            recentRepo.delete(r);
        }
        boardRepo.delete(board);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/board/title/{id}")
    public ResponseEntity<Board> getBoardTitleByID(@PathVariable String id) {
        Long boardID = Long.valueOf(id);
        Optional<Board> opBoard = boardRepo.findById(boardID);
        return opBoard.map(board -> new ResponseEntity<>(board, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/board/title/user/{email}")
    public ResponseEntity<Map<Long, String>> getBoardTitleOwner(@PathVariable String email) {
        // get user id
        Optional<User> users = userRepo.findByEmail(email);
        if (!users.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long id = users.get().getId();
        HashMap<Long, String> res = new HashMap<Long, String>();
        List<Board> allBoards = boardRepo.findAllBoard();
        if (allBoards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (Board board : allBoards) {
                Set<User> userSet = board.getUsers();
                for (User user : userSet) {
                    if (user.getId() == id) {
                        res.put(board.getId(), board.getTitle());
                    }
                }
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }


    @GetMapping("/api/board/member/{boardID}")
    public ResponseEntity<List<User>> getMemberID(@PathVariable String boardID) {
        Optional<Board> boards = boardRepo.findById(Long.valueOf(boardID));
        if (!boards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<User> users = new ArrayList<User>();
        Set<User> member = boards.get().getUsers();
        for (User u : member) {
            Optional<User> user = userRepo.findById(u.getId());
            if (!user.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                users.add(user.get());
            }
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/api/board/member/{boardID}/{email}")
    public ResponseEntity<Board> addNewMember(@PathVariable String boardID, @PathVariable String email) {
        Optional<User> users = userRepo.findByEmail(email);
        Optional<Board> boards = boardRepo.findById(Long.valueOf(boardID));
        if (!users.isPresent() || !boards.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = users.get();
        Board board = boards.get();
        Set<User> listUser = board.getUsers();
        int flag = 0;
        for (User u : listUser) {
            if (Objects.equals(u.getId(), user.getId())) {
                flag = 1;
                break;
            }
        }
        if (flag != 1) {
            listUser.add(user);
        }
        board.setUsers(listUser);
        boardRepo.save(board);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @GetMapping("/api/board/{idString}")
    public ResponseEntity<Board> getAllBoard(@PathVariable String idString) {
        Long id = Long.valueOf(idString);
        Optional<Board> boards = boardRepo.findById(id);
        return boards.map(board -> new ResponseEntity<>(board, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/board/title/{email}")
    public ResponseEntity<List<Board>> getAllBoardTitleByUserGmail(@PathVariable String email) {
        List<Board> boards = boardRepo.findByUserGmail(email);
        if (boards.isEmpty()) {
            System.out.println("no users email title");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(boards, HttpStatus.OK);
        }
    }

    @PostMapping("/board/title/{title}/{gmailOfUser}")
    public ResponseEntity<Board> addNewBoardTitle(@PathVariable String title, @PathVariable String gmailOfUser) {
        Optional<User> user = userRepo.findByEmail(gmailOfUser);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<User> setOfUser = new HashSet<User>();
        setOfUser.add(user.get());
        Board newBoard = new Board();
        newBoard.setTitle(title);
        newBoard.setUsers(setOfUser);
        return new ResponseEntity<>(boardRepo.save(newBoard), HttpStatus.OK);
    }

}
