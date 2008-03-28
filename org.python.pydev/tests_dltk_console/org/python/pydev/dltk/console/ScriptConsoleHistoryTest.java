package org.python.pydev.dltk.console;

import junit.framework.TestCase;

public class ScriptConsoleHistoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testScriptConsoleWithMatchingStart2() throws Exception {
        ScriptConsoleHistory c = new ScriptConsoleHistory();
        c.update("aaa");
        c.commit();
        
        c.update("bbb");
        c.commit();
        
        c.setMatchStart("a");
        assertTrue(c.prev());
        assertEquals("aaa", c.get());
        
        c.setMatchStart("b");
        assertTrue(c.prev()); //must cycle (will change other tests too)
        assertEquals("bbb", c.get());
    }
        
    
    public void testScriptConsoleWithMatchingStart() throws Exception {
        ScriptConsoleHistory c = new ScriptConsoleHistory();
        c.update("1. line1bbb");
        c.commit();
        
        c.update("1. line1aaa");
        c.commit();
        
        c.update("1. line2aaa");
        c.commit();
        
        c.update("1. line3aaa");
        c.commit();
        
        assertEquals("", c.get());
        c.setMatchStart("1. line1");
        
        assertTrue(c.prev());
        assertEquals("1. line1aaa", c.get());
        
        assertTrue(c.prev());
        assertEquals("1. line1bbb", c.get());
        
        assertFalse(c.prev());
        assertEquals("1. line1bbb", c.get());
        
        assertTrue(c.next());
        assertEquals("1. line1aaa", c.get());
        
        assertFalse(c.next());
        assertEquals("1. line1aaa", c.get());
    }
    
    public void testScriptConsole() throws Exception {
        ScriptConsoleHistory c = new ScriptConsoleHistory();
        assertFalse(c.prev());
        assertFalse(c.next());
        
        c.update("test");
        
        assertEquals("test", c.get());
        assertFalse(c.prev());
        assertEquals("test", c.get());
        assertFalse(c.next());
        assertEquals("test", c.get());
        
        c.commit();
        assertEquals("", c.get());
        assertTrue(c.prev());
        assertEquals("test", c.get());
        assertFalse(c.prev());
        assertEquals("test", c.get());
        assertTrue(c.next());
        assertEquals("", c.get());
        assertTrue(c.prev());
        assertEquals("test", c.get());
        
        c.update("kkk");
        c.commit();

        assertEquals("test\nkkk\n" , c.getAsDoc().get());
        assertFalse(c.next());
        assertEquals("", c.get());
        assertTrue(c.prev());
        assertEquals("kkk", c.get());
        assertTrue(c.prev());
        assertEquals("test", c.get());
        
        c.update("");
        c.commit();
        assertEquals("test\nkkk\n\n" , c.getAsDoc().get());
        assertTrue(c.prev());
        assertEquals("kkk", c.get());
    }
}
