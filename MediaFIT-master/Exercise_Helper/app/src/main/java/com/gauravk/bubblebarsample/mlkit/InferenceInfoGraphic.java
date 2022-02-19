/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gauravk.bubblebarsample.mlkit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.Nullable;

import com.gauravk.bubblebarsample.cfg.MyGlobal;

/** Graphic instance for rendering inference info (latency, FPS, resolution) in an overlay view. */
public class InferenceInfoGraphic extends GraphicOverlay.Graphic {

  private static final int TEXT_COLOR = Color.WHITE;
  private static final float TEXT_SIZE = 60.0f;

  private final Paint textPaint;
  private final GraphicOverlay overlay;
  private final long frameLatency;
  private final long detectorLatency;
  private int temp;
  // Only valid when a stream of input images is being processed. Null for single image mode.
  @Nullable private final Integer framesPerSecond;
  private boolean showLatencyInfo = true;

  public InferenceInfoGraphic(
      GraphicOverlay overlay,
      long frameLatency,
      long detectorLatency,
      @Nullable Integer framesPerSecond) {
    super(overlay);
    this.overlay = overlay;
    this.frameLatency = frameLatency;
    this.detectorLatency = detectorLatency;
    this.framesPerSecond = framesPerSecond;
    textPaint = new Paint();
    textPaint.setColor(TEXT_COLOR);
    textPaint.setTextSize(TEXT_SIZE);
    textPaint.setShadowLayer(5.0f, 0f, 0f, Color.BLACK);
    postInvalidate();
  }

  /** Creates an {@link InferenceInfoGraphic} to only display image size. */
  public InferenceInfoGraphic(GraphicOverlay overlay) {
    this(overlay, 0, 0, null);
    showLatencyInfo = false;
  }

  @Override
  public synchronized void draw(Canvas canvas) {
    float x = TEXT_SIZE * 0.5f+20;
    float y = TEXT_SIZE * 1.5f;
    /*
    canvas.drawText(
        "InputImage size: " + overlay.getImageHeight() + "x" + overlay.getImageWidth(),
        x,
        y,
        textPaint);

    if (!showLatencyInfo) {
      return;
    }
    // Draw FPS (if valid) and inference latency
    if (framesPerSecond != null) {
      canvas.drawText(
          "FPS: " + framesPerSecond + ", Frame latency: " + frameLatency + " ms",
          x,
          y + TEXT_SIZE,
          textPaint);
    } else {
      canvas.drawText("Frame latency: " + frameLatency + " ms", x, y + TEXT_SIZE, textPaint);
    }
    canvas.drawText(
        "Detector latency: " + detectorLatency + " ms", x, y + TEXT_SIZE * 2, textPaint);

     */
    temp = 1;
    canvas.drawText("현재 운동 : " + MyGlobal.getInstance().getExercise(),x,y,textPaint);
    if(MyGlobal.getInstance().getmode()==true){
      canvas.drawText("쉬는 시간 : " + MyGlobal.getInstance().getREST(),x,y+TEXT_SIZE,textPaint);
      canvas.drawText("남은 세트 : " + (MyGlobal.getInstance().getSET() - MyGlobal.getInstance().getnow_set()),
            x,y+TEXT_SIZE*2,textPaint);
      canvas.drawText("세트 남은 개수 : " + (MyGlobal.getInstance().getNum()-MyGlobal.getInstance().getNow_num()),
            x,y+TEXT_SIZE*3,textPaint);
      temp = 4;
    }
    canvas.drawText(  "FPS: " + framesPerSecond, x, y + TEXT_SIZE*temp, textPaint);
  }
}
