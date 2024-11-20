package org.codebase.taptictac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.codebase.taptictac.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TicTacToeGame game;
    private Map<Integer, ImageView> cellImageViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize game logic
        game = new TicTacToeGame();

        // Map cell positions to ImageView references
        cellImageViewMap = new HashMap<>();
        initializeCellMap();

        // Set initial status
        binding.statusTextView.setText(game.getCurrentPlayer() + "'s Turn");

        // Restart button listener
        binding.restartButton.setOnClickListener(v -> resetGame());
    }

    private void initializeCellMap() {
        cellImageViewMap.put(0, binding.imageView0);
        cellImageViewMap.put(1, binding.imageView1);
        cellImageViewMap.put(2, binding.imageView2);
        cellImageViewMap.put(3, binding.imageView3);
        cellImageViewMap.put(4, binding.imageView4);
        cellImageViewMap.put(5, binding.imageView5);
        cellImageViewMap.put(6, binding.imageView6);
        cellImageViewMap.put(7, binding.imageView7);
        cellImageViewMap.put(8, binding.imageView8);

        for (Map.Entry<Integer, ImageView> entry : cellImageViewMap.entrySet()) {
            entry.getValue().setOnClickListener(this::onCellTap);
        }
    }

    public void onCellTap(View view) {
        if (!game.isActive()) {
            resetGame();
            return;
        }

        ImageView tappedCell = (ImageView) view;
        int position = Integer.parseInt(tappedCell.getTag().toString());

        if (game.markCell(position)) {
            updateCell(tappedCell, game.getCurrentPlayer());
            binding.statusTextView.setText(game.getNextPlayer() + "'s Turn");

            if (game.checkWinner()) {
                displayWinner(game.getCurrentPlayer() + " Wins!");
                highlightWinningCells(game.getWinningCells());
            } else if (game.isDraw()) {
                displayWinner("It's a Draw!");
            } else {
                game.switchPlayer();
            }
        }
    }

    private void updateCell(ImageView cell, String player) {
        int drawable = player.equals("X") ? R.drawable.x : R.drawable.o;
        int color = player.equals("X") ? R.color.xColor : R.color.oColor;
        cell.setImageResource(drawable);
        cell.setImageTintList(ContextCompat.getColorStateList(this, color));
        cell.setEnabled(false); // Disable further clicks on this cell
    }

    private void highlightWinningCells(int[] winningCells) {
        for (int cell : winningCells) {
            ImageView img = cellImageViewMap.get(cell);
            if (img != null) {
                img.setBackground(ContextCompat.getDrawable(this, R.drawable.winning_cell_bg));
            }
        }
    }

    private void displayWinner(String message) {
        binding.statusTextView.setText(message);
        game.setActive(false);
    }

    private void resetGame() {
        game.reset();
        binding.statusTextView.setText("X's Turn - Tap to play");

        for (ImageView img : cellImageViewMap.values()) {
            img.setImageResource(0);
            img.setEnabled(true);
            img.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        }
    }
}
