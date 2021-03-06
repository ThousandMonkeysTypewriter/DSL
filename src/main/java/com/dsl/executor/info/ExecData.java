package com.dsl.executor.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.dsl.executor.Executor;

public class ExecData {
  HashMap<Integer, Step> buffer;
  int carrier;

  public ExecData() {
    buffer = new HashMap<Integer, Step>();
    carrier = 0;
    update_buffer();
  }
  
  private void update_buffer() {
    Step step = new Step();
    buffer.put(carrier, step);
  }
  
  private void update_carrier() {
    carrier += 1;
  }
  
  public void next_step() {
    Step st = buffer.get(carrier);
    update_carrier();
    update_buffer();
    copy_previous_buffer(st);
  }
  
  public void flush_buffer(ArrayList<HashMap<Integer, Step>> out) {
    out.add(buffer);
  }
  
  public void clear() {
    for (Entry<Integer, Step> b : buffer.entrySet())
      b.getValue().clear();
  }
  
  private void copy_previous_buffer(Step st) {
    buffer.get(carrier).fill_defaults(st);    
  }

  public void toEnvironment(String k, Object v, boolean visible) {
    buffer.get(carrier).supervised_env.put(k, new Element(k, v, Executor.ENVIRONMENT, visible));
  }
  
  public void toProgram(String k, Object v) {
    buffer.get(carrier).program.put(k, new Element(k, v, Executor.PROGRAM, true));
  }
  
  public void toArgument(String k, Object v) {
    buffer.get(carrier).argument.put(k, new Element(k, v, Executor.ARGUMENT, true));
  }
  
  public Element fromEnvironment(String k) {
    return buffer.get(carrier).supervised_env.get(k);
  }
  
  public Element fromArgument(String k) {
    return buffer.get(carrier).argument.get(k);
  }
  
  public Element fromProgram(String k) {
    return buffer.get(carrier).program.get(k);
  }
}