import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NoteElementEditorComponent } from './note-element-editor.component';

describe('NoteElementEditorComponent', () => {
  let component: NoteElementEditorComponent;
  let fixture: ComponentFixture<NoteElementEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NoteElementEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteElementEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
